package com.hathway.ramadankareem2026.ui.quran.bookmarks

import androidx.lifecycle.ViewModel
import com.hathway.ramadankareem2026.ui.quran.domain.repository.BookmarkRepository

class SavedAyahsViewModel(
    bookmarkRepository: BookmarkRepository
) : ViewModel() {

    val savedAyahs = bookmarkRepository.bookmarkedAyahs
}
