package com.hathway.ramadankareem2026.core.service

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.hathway.ramadankareem2026.R
import com.hathway.ramadankareem2026.core.tts.TtsActions

object DuaTtsNotification {

    const val CHANNEL_ID = "dua_tts_channel"
    const val NOTIFICATION_ID = 101

    fun build(
        context: Context,
        isPlaying: Boolean
    ): Notification {

        val playIntent = Intent(context, DuaTtsService::class.java).apply {
            action = TtsActions.READ
        }

        val stopIntent = Intent(context, DuaTtsService::class.java).apply {
            action = TtsActions.STOP
        }

        val actionIntent = if (isPlaying) stopIntent else playIntent
        val actionIcon = if (isPlaying) R.drawable.ic_stop else R.drawable.ic_play
        val actionTitle = if (isPlaying) context.getString(R.string.stop) else context.getString(R.string.play)

        val pendingIntent = PendingIntent.getService(
            context,
            0,
            actionIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.bell)
            .setContentTitle(context.getString(R.string.dua_reader))
            .setContentText(context.getString(R.string.arabic_dua_audio))
            .addAction(actionIcon, actionTitle, pendingIntent)
            .setOngoing(isPlaying)
            .build()
    }
}
