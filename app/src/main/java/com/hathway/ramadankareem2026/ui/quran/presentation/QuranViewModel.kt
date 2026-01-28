package com.hathway.ramadankareem2026.ui.quran.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hathway.ramadankareem2026.ui.quran.data.local.LastReadDataStore
import com.hathway.ramadankareem2026.ui.quran.domain.model.Surah
import com.hathway.ramadankareem2026.ui.quran.domain.repository.BookmarkRepository
import com.hathway.ramadankareem2026.ui.quran.domain.usecase.GetAyahListUseCase
import com.hathway.ramadankareem2026.ui.quran.domain.usecase.GetSurahListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class QuranViewModel(
    private val getSurahList: GetSurahListUseCase,
    private val getAyahs: GetAyahListUseCase,
    private val bookmarkRepository: BookmarkRepository,
    private val lastReadStore: LastReadDataStore
) : ViewModel() {

    private val _state = MutableStateFlow(QuranUiState())
    val state = _state.asStateFlow()


    val lastReadAyah = lastReadStore.lastReadAyah
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    val bookmarkedAyahs = bookmarkRepository.bookmarkedAyahs.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptySet()
    )

    init {
        loadInitialData()
    }

    private fun loadInitialData() = viewModelScope.launch {

        // 🔹 Tell UI we are loading
        _state.value = _state.value.copy(
            isLoading = true,
            errorMessage = null
        )

        try {
            val surahs = getSurahList()

            val selected = surahs.firstOrNull()

            _state.value = _state.value.copy(
                isLoading = false,
                surahList = surahs,
                selectedSurah = selected,
                ayahs = selected?.let { getAyahs(it.id) } ?: emptyList()
            )

        } catch (e: Exception) {
            _state.value = _state.value.copy(
                isLoading = false,
                errorMessage = "Failed to load Quran data"
            )
        }
    }



    fun onSurahSelected(surah: Surah) = viewModelScope.launch {
        val ayahs = getAyahs(surah.id)

        _state.value = _state.value.copy(
            selectedSurah = surah,
            ayahs = ayahs
        )
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
}
