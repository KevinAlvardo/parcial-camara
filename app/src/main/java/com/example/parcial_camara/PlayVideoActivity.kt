package com.example.parcial_camara



import android.net.Uri
import android.os.Bundle
import android.widget.MediaController
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity

class PlayVideoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_video)

        val videoView: VideoView = findViewById(R.id.videoView)
        val videoPath = intent.getStringExtra("videoPath")

        if (videoPath != null) {
            val videoUri = Uri.parse(videoPath)
            videoView.setVideoURI(videoUri)

            val mediaController = MediaController(this)
            mediaController.setAnchorView(videoView)
            videoView.setMediaController(mediaController)
            videoView.start()
        }
    }
}
