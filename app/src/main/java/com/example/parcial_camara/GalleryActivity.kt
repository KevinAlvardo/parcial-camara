package com.example.parcial_camara

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File

class GalleryActivity : AppCompatActivity() {

    private lateinit var recyclerViewGallery: RecyclerView
    private lateinit var galleryAdapter: GalleryAdapter
    private lateinit var mediaList: MutableList<MediaItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

        recyclerViewGallery = findViewById(R.id.recyclerViewGallery)
        recyclerViewGallery.layoutManager = LinearLayoutManager(this)

        mediaList = getMediaItems()
        galleryAdapter = GalleryAdapter(mediaList)
        recyclerViewGallery.adapter = galleryAdapter

        // Configurar el botón para eliminar elementos seleccionados
        val btnDeleteSelected = findViewById<Button>(R.id.btnDeleteSelected)
        btnDeleteSelected.setOnClickListener {
            deleteSelectedItems()
        }
    }

    private fun getMediaItems(): MutableList<MediaItem> {
        val mediaItems = mutableListOf<MediaItem>()
        val mediaDir = File("/storage/emulated/0/Android/media/com.example.parcial_camara/parcial-camara")

        if (mediaDir.exists() && mediaDir.isDirectory) {
            mediaDir.listFiles()?.forEach { file ->
                val isVideo = file.extension == "mp4"
                mediaItems.add(MediaItem(file.path, isVideo))
            }
        }

        return mediaItems
    }

    private fun deleteSelectedItems() {
        // Eliminar los archivos seleccionados y actualiza la lista
        val selectedItems = galleryAdapter.selectedItems
        selectedItems.forEach { mediaItem ->
            val file = File(mediaItem.filePath)
            if (file.exists()) {
                file.delete()
            }
        }

        // Elimina los elementos de la lista y actualiza el adaptador
        mediaList.removeAll(selectedItems)
        galleryAdapter.selectedItems.clear()
        galleryAdapter.notifyDataSetChanged()

        Toast.makeText(this, "Elementos eliminados", Toast.LENGTH_SHORT).show()
    }
}
