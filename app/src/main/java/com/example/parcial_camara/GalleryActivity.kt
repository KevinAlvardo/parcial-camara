package com.example.parcial_camara

import android.media.browse.MediaBrowser
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File

class GalleryActivity : AppCompatActivity() {

    private lateinit var recyclerViewGallery: RecyclerView
    private lateinit var galleryAdapter: GalleryAdapter
    private val mediaList = mutableListOf<MediaBrowser.MediaItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

        recyclerViewGallery = findViewById(R.id.recyclerViewGallery)
        recyclerViewGallery.layoutManager = LinearLayoutManager(this)

        // Cargar fotos y videos en la lista
        loadMediaFiles()

        // Configurar el adaptador con la lista de media
        galleryAdapter = GalleryAdapter(mediaList)
        recyclerViewGallery.adapter = galleryAdapter
    }

    private fun loadMediaFiles() {
        // Aquí deberás obtener los archivos de la carpeta de almacenamiento de fotos y videos
        val mediaDir = File(getExternalFilesDir(null), "Media")
        if (mediaDir.exists()) {
            mediaDir.listFiles()?.forEach { file ->
                val isVideo = file.extension == "mp4"
                mediaList.add(MediaBrowser.MediaItem(file.absolutePath, isVideo))
            }
        }
    }
}
