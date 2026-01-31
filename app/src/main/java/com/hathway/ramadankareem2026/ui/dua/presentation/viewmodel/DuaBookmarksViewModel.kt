package com.hathway.ramadankareem2026.ui.dua.presentation.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.hathway.ramadankareem2026.data.local.BookmarkManager
import com.hathway.ramadankareem2026.data.local.database.BookmarkDao
import com.hathway.ramadankareem2026.data.local.database.BookmarkEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DuaBookmarksViewModel(application: Application) : AndroidViewModel(application) {

    private val bookmarkDao: BookmarkDao = BookmarkManager.getDatabase(application).bookmarkDao()

    private val _duaBookmarks = MutableStateFlow<List<BookmarkEntity>>(emptyList())
    val duaBookmarks: StateFlow<List<BookmarkEntity>> = _duaBookmarks.asStateFlow()

    init {
        loadDuaBookmarks()
    }

    private fun loadDuaBookmarks() {
        viewModelScope.launch {
            Log.d("DuaBookmarks", "=== COMPREHENSIVE DATABASE ANALYSIS START ===")
            
            // Debug: Check all bookmarks in database
            try {
                val allBookmarks = bookmarkDao.getAllBookmarks()
                Log.d("DuaBookmarks", "TOTAL bookmarks in database: ${allBookmarks.size}")
                
                // Group by itemType for analysis
                val groupedByType = allBookmarks.groupBy { it.itemType }
                groupedByType.forEach { (type, bookmarks) ->
                    Log.d("DuaBookmarks", "TYPE '$type': ${bookmarks.size} bookmarks")
                    bookmarks.forEach { bookmark ->
                        Log.d("DuaBookmarks", "  - id=${bookmark.id}, itemId=${bookmark.itemId}, title=${bookmark.title}")
                    }
                }
                
                // Specifically check dua bookmarks
                val duaBookmarksFromAll = allBookmarks.filter { it.itemType == "dua" }
                Log.d("DuaBookmarks", "ACTUAL dua bookmarks in database: ${duaBookmarksFromAll.size}")
                duaBookmarksFromAll.forEach { bookmark ->
                    Log.d("DuaBookmarks", "ACTUAL DUA: id=${bookmark.id}, itemId=${bookmark.itemId}, title=${bookmark.title}")
                }
                
            } catch (e: Exception) {
                Log.e("DuaBookmarks", "Error getting all bookmarks: ${e.message}")
            }
            
            // Now test the specific query that should return only dua bookmarks
            Log.d("DuaBookmarks", "=== TESTING DATABASE QUERY ===")
            bookmarkDao.getBookmarksByType("dua").collect { queryResult ->
                Log.d("DuaBookmarks", "QUERY RESULT: Found ${queryResult.size} bookmarks with itemType='dua'")
                queryResult.forEach { bookmark ->
                    Log.d("DuaBookmarks", "QUERY RESULT: id=${bookmark.id}, itemId=${bookmark.itemId}, type=${bookmark.itemType}, title=${bookmark.title}")
                }
                
                // Check if query is returning wrong types
                val wrongTypes = queryResult.filter { it.itemType != "dua" }
                if (wrongTypes.isNotEmpty()) {
                    Log.e("DuaBookmarks", "❌ CRITICAL ERROR: Query returned ${wrongTypes.size} bookmarks with wrong itemType!")
                    wrongTypes.forEach { bookmark ->
                        Log.e("DuaBookmarks", "❌ WRONG TYPE: id=${bookmark.id}, itemId=${bookmark.itemId}, type=${bookmark.itemType}, title=${bookmark.title}")
                    }
                }
                
                // Additional safety filter: ensure only actual dua bookmarks are shown
                val filteredBookmarks = queryResult.filter { it.itemType == "dua" }
                Log.d("DuaBookmarks", "✅ SAFETY FILTER: ${filteredBookmarks.size} actual dua bookmarks after filtering")
                
                _duaBookmarks.value = filteredBookmarks.sortedByDescending { it.createdAt }
                Log.d("DuaBookmarks", "✅ FINAL RESULT: Set dua bookmarks list with ${_duaBookmarks.value.size} items")
                Log.d("DuaBookmarks", "=== COMPREHENSIVE DATABASE ANALYSIS END ===")
            }
        }
    }
}
