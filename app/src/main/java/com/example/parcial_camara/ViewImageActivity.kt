package com.example.parcial_camara

import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import java.io.File


class ViewImageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_image)

        val imageView: ImageView = findViewById(R.id.imageView)
        val imagePath = intent.getStringExtra("imagePath")

        if (imagePath != null) {
            val imageUri = Uri.fromFile(File(imagePath))
            imageView.setImageURI(imageUri)
        }
    }
}