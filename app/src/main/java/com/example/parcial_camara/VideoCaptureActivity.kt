package com.example.parcial_camara

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.core.VideoCapture
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.video.FileOutputOptions
import androidx.camera.video.Quality
import androidx.camera.video.QualitySelector
import androidx.camera.video.Recorder
import androidx.camera.video.Recording
import androidx.camera.video.VideoRecordEvent
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import java.io.File

class VideoCaptureActivity : AppCompatActivity() {

    private lateinit var videoCapture: VideoCapture<Recorder>
    private lateinit var previewView: PreviewView
    private var activeRecording: Recording? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_capture)

        previewView = findViewById(R.id.previewView)
        val btnRecordVideo = findViewById<Button>(R.id.btnRecordVideo)

        startCamera()

        btnRecordVideo.setOnClickListener {
            if (activeRecording == null) {
                startRecording()
                btnRecordVideo.text = "Detener"
            } else {
                stopRecording()
                btnRecordVideo.text = "Grabar Video"
            }
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(previewView.surfaceProvider)
            }

            val recorder = Recorder.Builder().setQualitySelector(QualitySelector.from(Quality.HD)).build()
            videoCapture = VideoCapture.withOutput(recorder)
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(this, cameraSelector, preview, videoCapture)
        }, ContextCompat.getMainExecutor(this))
    }

    private fun startRecording() {
        val videoFile = File(getOutputDirectory(), "VID_${System.currentTimeMillis()}.mp4")
        val outputOptions = FileOutputOptions.Builder(videoFile).build()

        activeRecording = videoCapture.output.prepareRecording(this, outputOptions)
            .start(ContextCompat.getMainExecutor(this)) { recordEvent ->
                if (recordEvent is VideoRecordEvent.Finalize) {
                    Toast.makeText(this, "Video guardado", Toast.LENGTH_SHORT).show()
                    activeRecording = null
                }
            }
    }

    private fun stopRecording() {
        activeRecording?.stop()
        activeRecording = null
    }
}
