package com.hathway.ramadankareem2026.core.service


import android.Manifest
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationManagerCompat
import com.hathway.ramadankareem2026.core.tts.TtsActions
import java.util.Locale

class DuaTtsService : Service(), TextToSpeech.OnInitListener {

    private lateinit var tts: TextToSpeech
    private lateinit var notificationManager: NotificationManager

    override fun onCreate() {
        super.onCreate()

        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        tts = TextToSpeech(this, this)

        tts.setOnUtteranceProgressListener(object : UtteranceProgressListener() {

            @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
            override fun onStart(utteranceId: String?) {
                updateNotification(isPlaying = true)
                sendStateBroadcast(true)
            }

            @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
            override fun onDone(utteranceId: String?) {
                updateNotification(isPlaying = false)
                sendStateBroadcast(false)
            }

            @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
            override fun onError(utteranceId: String?) {
                updateNotification(isPlaying = false)
                sendStateBroadcast(false)
            }
        })
    }

    private fun sendStateBroadcast(isPlaying: Boolean) {
        val broadcastIntent = Intent("com.hathway.ramadankareem2026.TTS_STATE_CHANGED").apply {
            putExtra("isPlaying", isPlaying)
        }
        sendBroadcast(broadcastIntent)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            tts.language = Locale("ar") // ✅ Arabic only
        }
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        when (intent?.action) {

            TtsActions.READ -> {
                val arabicText =
                    intent.getStringExtra(TtsActions.EXTRA_TEXT) ?: return START_NOT_STICKY

                tts.speak(
                    arabicText, TextToSpeech.QUEUE_FLUSH, null, "dua_arabic_once" // 👈 unique ID
                )
            }

            TtsActions.STOP -> {
                tts.stop()
                updateNotification(isPlaying = false)
                sendStateBroadcast(false)
            }
        }

        return START_NOT_STICKY
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    private fun updateNotification(isPlaying: Boolean) {
        val notification = DuaTtsNotification.build(
            context = this, isPlaying = isPlaying
        )

        NotificationManagerCompat.from(this)
            .notify(DuaTtsNotification.NOTIFICATION_ID, notification)
    }

    override fun onDestroy() {
        try {
            tts.stop()
            tts.shutdown()
        } catch (_: Exception) {}
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
