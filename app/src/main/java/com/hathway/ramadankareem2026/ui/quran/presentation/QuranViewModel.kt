package com.hathway.ramadankareem2026.ui.quran.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hathway.ramadankareem2026.ui.quran.data.local.LastReadDataStore
import com.hathway.ramadankareem2026.ui.quran.domain.model.Ayah
import com.hathway.ramadankareem2026.ui.quran.domain.model.Surah
import com.hathway.ramadankareem2026.ui.quran.domain.repository.BookmarkRepository
import com.hathway.ramadankareem2026.ui.quran.domain.usecase.GetAyahListUseCase
import com.hathway.ramadankareem2026.ui.quran.domain.usecase.GetSurahListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class QuranViewModel(
    private val getSurahList: GetSurahListUseCase,
    private val getAyahs: GetAyahListUseCase,
    private val bookmarkRepository: BookmarkRepository,
    private val lastReadStore: LastReadDataStore,
    private val appContext: Context
) : ViewModel() {

    // SINGLE AUDIO SOURCE (SURAH LEVEL)
    private val audioController = QuranAudioController(appContext)

    // AUDIO STATE (SOURCE OF TRUTH)
    val isAudioPlaying: StateFlow<Boolean> = audioController.isPlaying
    val currentPlayingIndex: StateFlow<Int> = audioController.currentIndex

    // PLAYBACK LIFECYCLE
    private val _hasStartedPlayback = MutableStateFlow(false)
    val hasStartedPlayback: StateFlow<Boolean> = _hasStartedPlayback.asStateFlow()

    // UI STATE
    private val _state = MutableStateFlow(QuranUiState())
    val state = _state.asStateFlow()

    // LAST READ
    val lastReadAyah = lastReadStore.lastReadAyah
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    // BOOKMARKS
    val bookmarkedAyahs = bookmarkRepository.bookmarkedAyahs
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptySet()
        )

    init {
        loadSurahs()
    }

    // ================= AUDIO CONTROLS =================

    fun playSurah(ayahs: List<Ayah>, startIndex: Int = 0) {
        val urls = ayahs.mapNotNull { it.audio }
        if (urls.isEmpty()) return

        audioController.playAyahs(urls, startIndex)
        _hasStartedPlayback.value = true
    }

    fun pauseAudio() {
        audioController.pause()
    }

    fun resumeAudio() {
        audioController.resume()
    }

    fun stopAudio() {
        audioController.stop()
        _hasStartedPlayback.value = false
    }

    // ================= DATA =================

    fun loadSurahs() = viewModelScope.launch {
        _state.value = _state.value.copy(isLoading = true, errorMessage = null)

        try {
            val surahs = getSurahList()
            _state.value = _state.value.copy(
                isLoading = false,
                surahList = surahs
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
            selectedSurah = surah,
            ayahs = emptyList(),
            errorMessage = null
        )

        try {
            val ayahs = getAyahs(surah.id)
            _state.value = _state.value.copy(
                isLoading = false,
                ayahs = ayahs
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

    // ================= BOOKMARKS =================

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

    override fun onCleared() {
        super.onCleared()
        audioController.release()
    }
}
