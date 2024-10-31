package com.example.parcial_camara

import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import java.io.File

class PhotoViewerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_viewer)

        val imageViewPhoto = findViewById<ImageView>(R.id.imageViewPhoto)
        val photoPath = intent.getStringExtra("PHOTO_PATH")

        if (photoPath != null) {
            val photoFile = File(photoPath)
            if (photoFile.exists()) {
                val bitmap = BitmapFactory.decodeFile(photoFile.absolutePath)
                // Ajusta la orientación antes de mostrarla
                val orientedBitmap = adjustBitmapOrientation(photoFile.absolutePath, bitmap)
                imageViewPhoto.setImageBitmap(orientedBitmap)
            }
        }
    }

    private fun adjustBitmapOrientation(filePath: String, bitmap: android.graphics.Bitmap): android.graphics.Bitmap {
        val ei = androidx.exifinterface.media.ExifInterface(filePath)
        val orientation = ei.getAttributeInt(
            androidx.exifinterface.media.ExifInterface.TAG_ORIENTATION,
            androidx.exifinterface.media.ExifInterface.ORIENTATION_UNDEFINED
        )
        return when (orientation) {
            androidx.exifinterface.media.ExifInterface.ORIENTATION_ROTATE_90 -> rotateBitmap(bitmap, 90f)
            androidx.exifinterface.media.ExifInterface.ORIENTATION_ROTATE_180 -> rotateBitmap(bitmap, 180f)
            androidx.exifinterface.media.ExifInterface.ORIENTATION_ROTATE_270 -> rotateBitmap(bitmap, 270f)
            else -> bitmap
        }
    }

    private fun rotateBitmap(bitmap: android.graphics.Bitmap, angle: Float): android.graphics.Bitmap {
        val matrix = android.graphics.Matrix().apply { postRotate(angle) }
        return android.graphics.Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }
}
