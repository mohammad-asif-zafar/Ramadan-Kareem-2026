package com.hathway.ramadankareem2026.ui.quran.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.hathway.ramadankareem2026.data.local.BookmarkManager
import com.hathway.ramadankareem2026.data.local.database.BookmarkDao
import com.hathway.ramadankareem2026.data.local.database.BookmarkEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class QuranBookmarksViewModel(application: Application) : AndroidViewModel(application) {

    private val bookmarkDao: BookmarkDao = BookmarkManager.getDatabase(application).bookmarkDao()

    private val _quranBookmarks = MutableStateFlow<List<BookmarkEntity>>(emptyList())
    val quranBookmarks: StateFlow<List<BookmarkEntity>> = _quranBookmarks.asStateFlow()

    init {
        loadQuranBookmarks()
    }

    private fun loadQuranBookmarks() {
        viewModelScope.launch {
            bookmarkDao.getBookmarksByType("quran").collect { bookmarks ->
                _quranBookmarks.value = bookmarks.sortedByDescending { it.createdAt }
            }
        }
    }
}
