package com.example.simplemusic

import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val musicPlayer : MediaPlayer? = MediaPlayer.create(this, R.raw.chocolate)
        musicPlayer?.start()

        val myUri : Uri = Uri.parse("uri")//"uri 초기화"
        val mediaPlayer : MediaPlayer? = MediaPlayer().apply {
            setAudioStreamType(AudioManager.STREAM_MUSIC)
            setDataSource(applicationContext, myUri)
            prepare()
            start()
        }
    }
}