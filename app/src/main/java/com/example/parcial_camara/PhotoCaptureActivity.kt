package com.example.parcial_camara

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Button
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File
import java.io.FileOutputStream

class PhotoCaptureActivity : AppCompatActivity() {

    private lateinit var imageCapture: ImageCapture
    private lateinit var previewView: PreviewView
    private lateinit var btnSwitchCamera: Button
    private lateinit var zoomSeekBar: SeekBar
    private lateinit var btnFlashToggle: Button
    private lateinit var btnApplyFilter: Button
    private var camera: Camera? = null
    private var isUsingBackCamera = true
    private lateinit var cameraProvider: ProcessCameraProvider
    private var flashMode = ImageCapture.FLASH_MODE_OFF
    private var isFilterEnabled = false  // Variable para activar/desactivar el filtro

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_capture)

        previewView = findViewById(R.id.previewView)
        val btnCapturePhoto = findViewById<Button>(R.id.btnCapturePhoto)
        btnSwitchCamera = findViewById(R.id.btnSwitchCamera)
        zoomSeekBar = findViewById(R.id.zoomSeekBar)
        btnFlashToggle = findViewById(R.id.btnFlashToggle)
        btnApplyFilter = findViewById(R.id.btnApplyFilter)

        requestCameraPermission()

        btnCapturePhoto.setOnClickListener {
            takePhoto()
        }

        btnSwitchCamera.setOnClickListener {
            switchCamera()
        }

        btnFlashToggle.setOnClickListener {
            toggleFlashMode()
        }

        btnApplyFilter.setOnClickListener {
            toggleFilter()
        }

        zoomSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                camera?.let {
                    val maxZoomRatio = it.cameraInfo.zoomState.value?.maxZoomRatio ?: 1f
                    val minZoomRatio = it.cameraInfo.zoomState.value?.minZoomRatio ?: 1f
                    val zoomRatio = minZoomRatio + (progress / 100f) * (maxZoomRatio - minZoomRatio)
                    it.cameraControl.setZoomRatio(zoomRatio)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            cameraProvider = cameraProviderFuture.get()
            bindCameraUseCases()
        }, ContextCompat.getMainExecutor(this))
    }

    private fun bindCameraUseCases() {
        val preview = Preview.Builder().build().also {
            it.setSurfaceProvider(previewView.surfaceProvider)
        }

        imageCapture = ImageCapture.Builder()
            .setFlashMode(flashMode)
            .build()

        val cameraSelector = if (isUsingBackCamera) {
            CameraSelector.DEFAULT_BACK_CAMERA
        } else {
            CameraSelector.DEFAULT_FRONT_CAMERA
        }

        cameraProvider.unbindAll()
        camera = cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)
    }

    private fun switchCamera() {
        isUsingBackCamera = !isUsingBackCamera
        bindCameraUseCases()
    }

    private fun takePhoto() {
        val photoFile = File(getOutputDirectory(), "IMG_${System.currentTimeMillis()}.jpg")
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        imageCapture.takePicture(
            outputOptions, ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    if (isFilterEnabled) {
                        applyFilterToImage(photoFile)
                    }
                    Toast.makeText(applicationContext, "Foto guardada en ${photoFile.absolutePath}", Toast.LENGTH_SHORT).show()
                }

                override fun onError(exception: ImageCaptureException) {
                    Toast.makeText(applicationContext, "Error al capturar foto", Toast.LENGTH_SHORT).show()
                }
            }
        )
    }

    private fun applyFilterToImage(photoFile: File) {
        val bitmap = BitmapFactory.decodeFile(photoFile.absolutePath)
        val filteredBitmap = applyNaturalColorFilter(bitmap)
        FileOutputStream(photoFile).use { out ->
            filteredBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
        }
    }

    private fun applyNaturalColorFilter(bitmap: Bitmap): Bitmap {
        // Aplica el filtro para mejorar colores naturales (aumentando saturación y contraste)
        // Aquí se puede utilizar RenderScript o algún otro método de procesamiento de imágenes
        // Por ahora, solo devuelve el mismo bitmap (reemplazar con lógica de filtrado)
        return bitmap
    }

    private fun toggleFilter() {
        isFilterEnabled = !isFilterEnabled
        btnApplyFilter.text = if (isFilterEnabled) "Filtro: Activado" else "Filtro: Desactivado"
    }

    private fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }
        return mediaDir ?: filesDir
    }

    private fun requestCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), REQUEST_CAMERA_PERMISSION)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CAMERA_PERMISSION && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startCamera()
        } else {
            Toast.makeText(this, "Permiso de cámara denegado", Toast.LENGTH_SHORT).show()
        }
    }

    private fun toggleFlashMode() {
        flashMode = when (flashMode) {
            ImageCapture.FLASH_MODE_OFF -> ImageCapture.FLASH_MODE_ON
            ImageCapture.FLASH_MODE_ON -> ImageCapture.FLASH_MODE_AUTO
            else -> ImageCapture.FLASH_MODE_OFF
        }

        btnFlashToggle.text = when (flashMode) {
            ImageCapture.FLASH_MODE_OFF -> "Flash Off"
            ImageCapture.FLASH_MODE_ON -> "Flash On"
            else -> "Flash Auto"
        }

        imageCapture.flashMode = flashMode
    }

    companion object {
        const val REQUEST_CAMERA_PERMISSION = 1
    }
}
