package com.example.parcial_camara

import android.net.Uri
import android.os.Bundle
import android.widget.MediaController
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import java.io.File

class VideoPlayerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_player)

        val videoViewPlayer = findViewById<VideoView>(R.id.videoViewPlayer)
        val videoPath = intent.getStringExtra("VIDEO_PATH")

        if (videoPath != null) {
            val videoFile = File(videoPath)
            if (videoFile.exists()) {
                videoViewPlayer.setVideoURI(Uri.fromFile(videoFile))
                videoViewPlayer.setMediaController(MediaController(this))
                videoViewPlayer.start()
            }
        }
    }
}
