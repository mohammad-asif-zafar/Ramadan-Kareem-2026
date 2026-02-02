package com.hathway.ramadankareem2026.ui.dua.viewmodel

import android.app.Application
import android.os.Build
import android.speech.tts.TextToSpeech
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import java.util.Locale

class DuaTtsViewModel(application: Application) : AndroidViewModel(application),
    TextToSpeech.OnInitListener {

    private var tts: TextToSpeech? = null

    private val _isSpeaking = mutableStateOf(false)
    val isSpeaking: State<Boolean> = _isSpeaking

    init {
        tts = TextToSpeech(application, this)
    }

    @RequiresApi(Build.VERSION_CODES.BAKLAVA)
    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            // Arabic voice
            tts?.language = Locale.of("ar")
            tts?.setSpeechRate(0.9f)
        }
    }


    fun stop() {
        tts?.stop()
        _isSpeaking.value = false
    }

    fun speakArabicThenTranslation(arabic: String, translation: String) {
        tts?.language = Locale("ar")
        tts?.speak(arabic, TextToSpeech.QUEUE_FLUSH, null, "AR")

        tts?.language = Locale.ENGLISH
        tts?.speak(translation, TextToSpeech.QUEUE_ADD, null, "EN")

        _isSpeaking.value = true
    }

    override fun onCleared() {
        tts?.stop()
        tts?.shutdown()
        super.onCleared()
    }
}
