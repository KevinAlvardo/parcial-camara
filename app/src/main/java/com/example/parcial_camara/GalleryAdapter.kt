package com.example.parcial_camara

import android.content.Intent
import android.media.ThumbnailUtils
import android.net.Uri
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.io.File

class GalleryAdapter(private val mediaList: List<MediaItem>) : RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>() {

    // Almacenar los elementos seleccionados
    val selectedItems = mutableSetOf<MediaItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.gallery_item, parent, false)
        return GalleryViewHolder(view)
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        val mediaItem = mediaList[position]
        holder.bind(mediaItem)
    }

    override fun getItemCount(): Int = mediaList.size

    inner class GalleryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageThumbnail: ImageView = itemView.findViewById(R.id.imageThumbnail)
        private val tvFileName: TextView = itemView.findViewById(R.id.tvFileName)
        private val checkBoxSelect: CheckBox = itemView.findViewById(R.id.checkBoxSelect)

        fun bind(mediaItem: MediaItem) {
            tvFileName.text = File(mediaItem.filePath).name
            checkBoxSelect.isChecked = selectedItems.contains(mediaItem)

            // Configurar CheckBox para agregar o eliminar el elemento de la lista de seleccionados
            checkBoxSelect.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    selectedItems.add(mediaItem)
                } else {
                    selectedItems.remove(mediaItem)
                }
            }

            // Cargar miniatura
            if (mediaItem.isVideo) {
                val thumbnail = ThumbnailUtils.createVideoThumbnail(mediaItem.filePath, MediaStore.Images.Thumbnails.MINI_KIND)
                imageThumbnail.setImageBitmap(thumbnail)
            } else {
                imageThumbnail.setImageURI(Uri.fromFile(File(mediaItem.filePath)))
            }

            // Configurar click en el item para abrir la actividad correspondiente
            itemView.setOnClickListener {
                val context = itemView.context
                val intent = if (mediaItem.isVideo) {
                    Intent(context, VideoPlayerActivity::class.java).apply {
                        putExtra("VIDEO_PATH", mediaItem.filePath)
                    }
                } else {
                    Intent(context, PhotoViewerActivity::class.java).apply {
                        putExtra("PHOTO_PATH", mediaItem.filePath)
                    }
                }
                context.startActivity(intent)
            }
        }
    }
}
