package com.hathway.ramadankareem2026.ui.quran.presentation

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.hathway.ramadankareem2026.ui.quran.audio.AyahAudioManager
import com.hathway.ramadankareem2026.ui.quran.data.local.LastReadDataStore
import com.hathway.ramadankareem2026.ui.quran.domain.model.Ayah
import com.hathway.ramadankareem2026.ui.quran.domain.model.Surah
import com.hathway.ramadankareem2026.ui.quran.domain.repository.BookmarkRepository
import com.hathway.ramadankareem2026.ui.quran.domain.usecase.GetAyahListUseCase
import com.hathway.ramadankareem2026.ui.quran.domain.usecase.GetSurahListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow

class QuranViewModel(
    private val getSurahList: GetSurahListUseCase,
    private val getAyahs: GetAyahListUseCase,
    private val bookmarkRepository: BookmarkRepository,
    private val lastReadStore: LastReadDataStore,
    private val audioManager: AyahAudioManager = AyahAudioManager(),
    private val  appContext : Context
) : ViewModel(){

    private val audioController = QuranAudioController(appContext)

    val currentPlayingIndex: StateFlow<Int> = audioController.currentIndex

    fun playSurah(ayahs: List<Ayah>, startIndex: Int = 0) {
        val urls = ayahs.mapNotNull { it.audio }
        audioController.playAyahs(urls, startIndex)
    }

    fun pauseAudio() = audioController.pause()
    fun resumeAudio() = audioController.resume()
    fun stopAudio() = audioController.stop()


    private val _state = MutableStateFlow(QuranUiState())
    val state = _state.asStateFlow()

    // Audio playback state from AudioManager
    val currentlyPlayingAyah = audioManager.currentUrl
    val isAudioPlaying = audioManager.isPlaying

    val lastReadAyah = lastReadStore.lastReadAyah
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    val bookmarkedAyahs = bookmarkRepository.bookmarkedAyahs.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptySet()
    )

    init {
        loadSurahs()
    }

    fun loadSurahs() = viewModelScope.launch {

        // 🔹 Tell UI we are loading
        _state.value = _state.value.copy(
            isLoading = true,
            errorMessage = null
        )

        try {
            val surahs = getSurahList()
            _state.value = _state.value.copy(
                isLoading = false,
                surahList = surahs,
                errorMessage = null
            )

        } catch (e: Exception) {
            _state.value = _state.value.copy(
                isLoading = false,
                errorMessage = "Failed to load surahs"
            )
        }
    }

    fun loadAyahs(surah: Surah) = viewModelScope.launch {
        _state.value = _state.value.copy(
            isLoading = true,
            errorMessage = null,
            selectedSurah = surah,
            ayahs = emptyList()
        )

        try {
            val ayahs = getAyahs(surah.id)
            _state.value = _state.value.copy(
                isLoading = false,
                selectedSurah = surah,
                ayahs = ayahs,
                errorMessage = null
            )
        } catch (e: Exception) {
            _state.value = _state.value.copy(
                isLoading = false,
                errorMessage = "Failed to load ayahs"
            )
        }
    }

    fun loadAyahsById(surahId: Int) = viewModelScope.launch {
        val surah = _state.value.surahList.firstOrNull { it.id == surahId }
            ?: Surah(id = surahId)
        loadAyahs(surah)
    }



    fun toggleBookmark(surahId: Int, ayahNumber: Int) {
        val key = "$surahId:$ayahNumber"
        viewModelScope.launch {
            bookmarkRepository.toggleBookmark(key)
        }
    }

    fun saveLastRead(surahId: Int, ayahNumber: Int) {
        viewModelScope.launch {
            lastReadStore.saveLastRead(surahId, ayahNumber)
        }
    }

    fun toggleAudioPlayback(audioUrl: String?) {
        audioUrl?.let { url ->
            audioManager.togglePlayback(url)
        }
    }

    override fun onCleared() {
        super.onCleared()
        audioManager.release()
        audioController.release()
    }
}
