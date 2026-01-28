package com.hathway.ramadankareem2026.ui.quran.audio

import android.media.MediaPlayer
import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AyahAudioManager {
    private var mediaPlayer: MediaPlayer? = null
    private val _isPlaying = MutableStateFlow(false)
    val isPlaying = _isPlaying.asStateFlow()
    
    private val _currentUrl = MutableStateFlow<String?>(null)
    val currentUrl = _currentUrl.asStateFlow()
    
    private val TAG = "AyahAudioManager"

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
                    Log.d(TAG, "Audio started: $url")
                }
                
                setOnCompletionListener {
                    _isPlaying.value = false
                    _currentUrl.value = null
                    Log.d(TAG, "Audio completed")
                }
                
                setOnErrorListener { _, what, extra ->
                    Log.e(TAG, "Audio error: $what, $extra")
                    _isPlaying.value = false
                    _currentUrl.value = null
                    true
                }
                
                prepareAsync()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error playing audio", e)
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
                    Log.d(TAG, "Audio paused")
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error pausing audio", e)
        }
    }
    
    fun resumeAudio() {
        try {
            mediaPlayer?.let {
                if (!it.isPlaying) {
                    it.start()
                    _isPlaying.value = true
                    Log.d(TAG, "Audio resumed")
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error resuming audio", e)
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
            Log.d(TAG, "Audio stopped")
        } catch (e: Exception) {
            Log.e(TAG, "Error stopping audio", e)
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
