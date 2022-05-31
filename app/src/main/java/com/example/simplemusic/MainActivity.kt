package com.example.simplemusic

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val musicPlayer : MediaPlayer? = MediaPlayer.create(this, R.raw.chocolate)
        musicPlayer?.start()
    }
}