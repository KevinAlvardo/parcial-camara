package com.example.parcial_camara

import android.content.Intent
import android.media.ThumbnailUtils
import android.net.Uri
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.io.File

class GalleryAdapter(private val mediaList: List<MediaItem>) : RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>() {

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

        fun bind(mediaItem: MediaItem) {
            tvFileName.text = File(mediaItem.filePath).name

            if (mediaItem.isVideo) {
                // Cargar una miniatura de video
                val thumbnail = ThumbnailUtils.createVideoThumbnail(mediaItem.filePath, MediaStore.Images.Thumbnails.MINI_KIND)
                imageThumbnail.setImageBitmap(thumbnail)
                itemView.setOnClickListener {
                    // Abrir actividad para reproducir video
                    val intent = Intent(itemView.context, PlayVideoActivity::class.java)
                    intent.putExtra("videoPath", mediaItem.filePath)
                    itemView.context.startActivity(intent)
                }
            } else {
                // Cargar una miniatura de imagen
                imageThumbnail.setImageURI(Uri.fromFile(File(mediaItem.filePath)))
                itemView.setOnClickListener {
                    // Abrir actividad para ver imagen
                    val intent = Intent(itemView.context, ViewImageActivity::class.java)
                    intent.putExtra("imagePath", mediaItem.filePath)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }
}
