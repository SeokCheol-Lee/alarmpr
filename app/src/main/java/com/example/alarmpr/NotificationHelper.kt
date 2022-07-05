package com.example.alarmpr

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Build
import androidx.core.app.NotificationCompat

class NotificationHelper(base: Context?) : ContextWrapper(base){
    private val channelID = "channelID"
    private val channelNm = "channelNm"
    val mediaPlayer = MediaPlayer.create(this,R.raw.mi)

    init{
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            createChannel()
        }
    }

    private fun createChannel() {
        var channel = NotificationChannel(channelID,channelNm,
        NotificationManager.IMPORTANCE_DEFAULT)

        channel.enableLights(true)
        channel.enableVibration(true)
        channel.lightColor = Color.GREEN
        channel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE

        getManager().createNotificationChannel(channel)
    }

    fun getManager() : NotificationManager{
        return getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    }
    fun getChannelNotification(time: String?) : NotificationCompat.Builder{
        mediaPlayer.start()

        return NotificationCompat.Builder(applicationContext, channelID)
            .setContentTitle(time)
            .setContentText("내용")
            .setSmallIcon(R.drawable.ic_launcher_background)
    }
}