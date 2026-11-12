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

    var isEnded: Boolean = false
        private set

    init {
        player.addListener(object : Player.Listener {
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                _isPlaying.value = isPlaying
                if (isPlaying) {
                    isEnded = false
                }
            }

            override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                _currentIndex.value = player.currentMediaItemIndex
            }

            override fun onPlaybackStateChanged(playbackState: Int) {
                if (playbackState == Player.STATE_ENDED) {
                    isEnded = true
                    _isPlaying.value = false
                    player.seekTo(0, 0)
                    player.pause()
                }
            }
        })
    }

    fun playAyahs(audioUrls: List<String>, startIndex: Int = 0) {
        isEnded = false
        player.clearMediaItems()
        audioUrls.forEach {
            player.addMediaItem(MediaItem.fromUri(it))
        }
        player.prepare()
        player.seekTo(startIndex, 0)
        player.play()
    }

    fun pause() = player.pause()
    fun resume() {
        if (isEnded) {
            isEnded = false
            player.seekTo(0, 0)
        }
        player.play()
    }

    fun stop() {
        player.stop()
        player.clearMediaItems()
        _currentIndex.value = -1
        _isPlaying.value = false
        isEnded = false
    }

    fun release() {
        try {
            player.stop()
            player.clearMediaItems()
            player.release()
        } catch (_: Exception) {}
    }
}
