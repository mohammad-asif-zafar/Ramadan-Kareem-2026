package com.hathway.ramadankareem2026.ui.quran.audio

import android.media.MediaPlayer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AyahAudioManager {

    private var mediaPlayer: MediaPlayer? = null
    private val _isPlaying = MutableStateFlow(false)
    val isPlaying = _isPlaying.asStateFlow()
    
    private val _currentUrl = MutableStateFlow<String?>(null)
    val currentUrl = _currentUrl.asStateFlow()
    

    fun playAudio(url: String) {
        try {
            // Stop any current playback
            stopAudio()
            
            mediaPlayer = MediaPlayer().apply {
                setDataSource(url)
                setOnPreparedListener {
                    start()
                    _isPlaying.value = true
                    _currentUrl.value = url
                }
                
                setOnCompletionListener {
                    _isPlaying.value = false
                    _currentUrl.value = null
                }
                
                setOnErrorListener { _, what, extra ->
                    _isPlaying.value = false
                    _currentUrl.value = null
                    true
                }
                
                prepareAsync()
            }
        } catch (e: Exception) {
            _isPlaying.value = false
            _currentUrl.value = null
        }
    }
    
    fun pauseAudio() {
        try {
            mediaPlayer?.let {
                if (it.isPlaying) {
                    it.pause()
                    _isPlaying.value = false
                }
            }
        } catch (e: Exception) {
        }
    }
    
    fun resumeAudio() {
        try {
            mediaPlayer?.let {
                if (!it.isPlaying) {
                    it.start()
                    _isPlaying.value = true
                }
            }
        } catch (e: Exception) {
        }
    }
    
    fun stopAudio() {
        try {
            mediaPlayer?.let {
                if (it.isPlaying) {
                    it.stop()
                }
                it.release()
            }
            mediaPlayer = null
            _isPlaying.value = false
            _currentUrl.value = null
        } catch (e: Exception) {
        }
    }
    
    fun togglePlayback(url: String) {
        when {
            _currentUrl.value == url && _isPlaying.value -> {
                pauseAudio()
            }
            _currentUrl.value == url && !_isPlaying.value -> {
                resumeAudio()
            }
            else -> {
                playAudio(url)
            }
        }
    }
    
    fun release() {
        stopAudio()
    }
}
