package com.hathway.ramadankareem2026.ui.quran.presentation.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.hathway.ramadankareem2026.data.local.BookmarkManager
import com.hathway.ramadankareem2026.data.local.database.BookmarkDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class QuranBookmarkViewModel(application: Application) : AndroidViewModel(application) {

    private val bookmarkDao: BookmarkDao = BookmarkManager.getDatabase(application).bookmarkDao()

    private val _isBookmarkedMap = mutableMapOf<String, MutableStateFlow<Boolean>>()
    
    // Add callback for immediate badge updates
    private var onBookmarkCountChanged: ((Int) -> Unit)? = null

    fun setBookmarkCountChangedCallback(callback: (Int) -> Unit) {
        onBookmarkCountChanged = callback
    }

    fun isBookmarked(surahId: String): StateFlow<Boolean> {
        return _isBookmarkedMap.getOrPut(surahId) { MutableStateFlow(false) }.asStateFlow()
    }

    fun checkBookmarkStatus(surahId: String) {
        viewModelScope.launch {
            val isCurrentlyBookmarked = bookmarkDao.isBookmarked(surahId, "quran")
            Log.d("QuranBookmark", "Checking bookmark status for Surah $surahId: $isCurrentlyBookmarked")
            _isBookmarkedMap.getOrPut(surahId) { MutableStateFlow(false) }.value =
                isCurrentlyBookmarked
        }
    }

    fun toggleBookmark(surahId: String, title: String, content: String?) {
        viewModelScope.launch {
            val isCurrentlyBookmarked = bookmarkDao.isBookmarked(surahId, "quran")
            val stateFlow = _isBookmarkedMap.getOrPut(surahId) { MutableStateFlow(false) }

            Log.d("QuranBookmark", "Toggling bookmark for Surah $surahId: currently $isCurrentlyBookmarked")

            if (isCurrentlyBookmarked) {
                bookmarkDao.deleteBookmarkById(surahId, "quran")
                stateFlow.value = false
                Log.d("QuranBookmark", "Removed bookmark for Surah $surahId")
                
                // Trigger immediate Quran badge update with delta
                onBookmarkCountChanged?.invoke(-1)
            } else {
                val bookmark = com.hathway.ramadankareem2026.data.local.database.BookmarkEntity(
                    id = "quran_$surahId",
                    itemId = surahId,
                    itemType = "quran",
                    title = title,
                    content = content
                )
                bookmarkDao.insertBookmark(bookmark)
                stateFlow.value = true
                Log.d("QuranBookmark", "Added bookmark for Surah $surahId")
                
                // Trigger immediate Quran badge update with delta
                onBookmarkCountChanged?.invoke(1)
            }
        }
    }
}
