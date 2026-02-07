package com.hathway.ramadankareem2026.ui.quran.presentation

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class QuranAudioController(context: Context) {

    private val player = ExoPlayer.Builder(context).build()

    private val _currentIndex = MutableStateFlow(-1)
    val currentIndex: StateFlow<Int> = _currentIndex

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying

    init {
        player.addListener(object : Player.Listener {
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                _isPlaying.value = isPlaying
            }

            override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                _currentIndex.value = player.currentMediaItemIndex
            }
        })
    }

    fun playAyahs(audioUrls: List<String>, startIndex: Int = 0) {
        player.clearMediaItems()
        audioUrls.forEach {
            player.addMediaItem(MediaItem.fromUri(it))
        }
        player.prepare()
        player.seekTo(startIndex, 0)
        player.play()
    }

    fun pause() = player.pause()
    fun resume() = player.play()

    fun stop() {
        player.stop()
        _currentIndex.value = -1
    }

    fun release() {
        player.release()
    }
}
