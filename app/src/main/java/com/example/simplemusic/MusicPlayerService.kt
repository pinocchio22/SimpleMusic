package com.example.simplemusic

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.widget.Toast

/**
 * @author CHOI
 * @email vviian.2@gmail.com
 * @created 2022-06-02
 * @desc
 */
class MusicPlayerService : Service() {

    var mMediaPlayer : MediaPlayer ?= null  // 미디어 플레이어 객체를 null로 초기화
    var mBinder : MusicPlayerBinder = MusicPlayerBinder()

    inner class MusicPlayerBinder : Binder() {
        fun getService() : MusicPlayerService {
            return this@MusicPlayerService
        }
    }
    override fun onCreate() {   // 서비스가 생성될때 한번만 실행
        super.onCreate()
        startForegroundService()    // 포어그라운드 서비스 시작을 알리는 알림 생성
    }

    // 바인드
    override fun onBind(p0: Intent?): IBinder? {
        return mBinder
    }

    // 시작된 상태, 백그라운드
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    fun startForegroundService() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            val mChannel = NotificationChannel("CHANNEL_ID", "CHNNEL_NAME", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(mChannel)
        }

        val notification : Notification = Notification.Builder(this, "CHANNEL_ID")
            .setSmallIcon(R.drawable.ic_play)
            .setContentTitle("뮤직 플레이어")
            .setContentText("앱이 실행중 입니다.")
            .build()

        startForeground(1,notification)
    }

    // 서비스 종료
    override fun onDestroy() {
        super.onDestroy()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            stopForeground(true)
        }
    }

    // 재생중인지 확인
    fun isPlaying() : Boolean {
        return (mMediaPlayer != null && mMediaPlayer?.isPlaying ?: false)
    }
    // 재생
    fun play() {
        if (mMediaPlayer == null) {
            mMediaPlayer = MediaPlayer.create(this, R.raw.chocolate)

            mMediaPlayer?.setVolume(1.0f, 1.0f)
            mMediaPlayer?.isLooping = true
            mMediaPlayer?.start()
        } else {
            if (mMediaPlayer!!.isPlaying) {
                Toast.makeText(this, "이미 실행중", Toast.LENGTH_SHORT).show()
            } else {
                mMediaPlayer?.start()
            }
        }
    }

    // 일시정지
    fun pause() {
        mMediaPlayer?.let {
            if (it.isPlaying) {
                it.pause()
            }
        }
    }

    // 완전 정지
    fun stop() {
        mMediaPlayer?.let {
            if (it.isPlaying) {
                it.stop()
                it.release()    // 할당된 자원을 해제
                mMediaPlayer = null
            }
        }
    }
 }