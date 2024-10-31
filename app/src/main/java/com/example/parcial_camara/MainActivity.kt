package com.example.parcial_camara

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnCapturePhoto = findViewById<Button>(R.id.btnCapturePhoto)
        val btnRecordVideo = findViewById<Button>(R.id.btnRecordVideo)
        val btnViewGallery = findViewById<Button>(R.id.btnViewGallery)

        btnCapturePhoto.setOnClickListener {
            // Lanzar la actividad para capturar una foto
            val intent = Intent(this, PhotoCaptureActivity::class.java)
            startActivity(intent)
        }

        btnRecordVideo.setOnClickListener {
            // Lanzar la actividad para grabar un video
            val intent = Intent(this, VideoCaptureActivity::class.java)
            startActivity(intent)
        }

        btnViewGallery.setOnClickListener {
            // Lanzar la actividad para ver la galería de fotos y videos
            val intent = Intent(this, GalleryActivity::class.java)
            startActivity(intent)
        }
    }
}
