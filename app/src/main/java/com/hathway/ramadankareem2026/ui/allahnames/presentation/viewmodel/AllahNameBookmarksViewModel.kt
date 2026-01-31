package com.hathway.ramadankareem2026.ui.allahnames.presentation.viewmodel

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

class AllahNameBookmarksViewModel(application: Application) : AndroidViewModel(application) {

    private val bookmarkDao: BookmarkDao = BookmarkManager.getDatabase(application).bookmarkDao()

    private val _allahNameBookmarks = MutableStateFlow<List<BookmarkEntity>>(emptyList())
    val allahNameBookmarks: StateFlow<List<BookmarkEntity>> = _allahNameBookmarks.asStateFlow()

    init {
        loadAllahNameBookmarks()
    }

    private fun loadAllahNameBookmarks() {
        viewModelScope.launch {
            Log.d("AllahNameBookmarks", "=== COMPREHENSIVE DATABASE ANALYSIS START ===")
            
            // Debug: Check all bookmarks in database
            try {
                val allBookmarks = bookmarkDao.getAllBookmarks()
                Log.d("AllahNameBookmarks", "TOTAL bookmarks in database: ${allBookmarks.size}")
                
                // Group by itemType for analysis
                val groupedByType = allBookmarks.groupBy { it.itemType }
                groupedByType.forEach { (type, bookmarks) ->
                    Log.d("AllahNameBookmarks", "TYPE '$type': ${bookmarks.size} bookmarks")
                    bookmarks.forEach { bookmark ->
                        Log.d("AllahNameBookmarks", "  - id=${bookmark.id}, itemId=${bookmark.itemId}, title=${bookmark.title}")
                    }
                }
                
                // Specifically check allah_name bookmarks
                val allahNameBookmarksFromAll = allBookmarks.filter { it.itemType == "allah_name" }
                Log.d("AllahNameBookmarks", "ACTUAL allah_name bookmarks in database: ${allahNameBookmarksFromAll.size}")
                allahNameBookmarksFromAll.forEach { bookmark ->
                    Log.d("AllahNameBookmarks", "ACTUAL ALLAH_NAME: id=${bookmark.id}, itemId=${bookmark.itemId}, title=${bookmark.title}")
                }
                
            } catch (e: Exception) {
                Log.e("AllahNameBookmarks", "Error getting all bookmarks: ${e.message}")
            }
            
            // Now test the specific query that should return only allah_name bookmarks
            Log.d("AllahNameBookmarks", "=== TESTING DATABASE QUERY ===")
            bookmarkDao.getBookmarksByType("allah_name").collect { queryResult ->
                Log.d("AllahNameBookmarks", "QUERY RESULT: Found ${queryResult.size} bookmarks with itemType='allah_name'")
                queryResult.forEach { bookmark ->
                    Log.d("AllahNameBookmarks", "QUERY RESULT: id=${bookmark.id}, itemId=${bookmark.itemId}, type=${bookmark.itemType}, title=${bookmark.title}")
                }
                
                // Check if query is returning wrong types
                val wrongTypes = queryResult.filter { it.itemType != "allah_name" }
                if (wrongTypes.isNotEmpty()) {
                    Log.e("AllahNameBookmarks", "❌ CRITICAL ERROR: Query returned ${wrongTypes.size} bookmarks with wrong itemType!")
                    wrongTypes.forEach { bookmark ->
                        Log.e("AllahNameBookmarks", "❌ WRONG TYPE: id=${bookmark.id}, itemId=${bookmark.itemId}, type=${bookmark.itemType}, title=${bookmark.title}")
                    }
                }
                
                // Additional safety filter: ensure only actual allah_name bookmarks are shown
                val filteredBookmarks = queryResult.filter { it.itemType == "allah_name" }
                Log.d("AllahNameBookmarks", "✅ SAFETY FILTER: ${filteredBookmarks.size} actual allah_name bookmarks after filtering")
                
                _allahNameBookmarks.value = filteredBookmarks.sortedByDescending { it.createdAt }
                Log.d("AllahNameBookmarks", "✅ FINAL RESULT: Set allah_name bookmarks list with ${_allahNameBookmarks.value.size} items")
                Log.d("AllahNameBookmarks", "=== COMPREHENSIVE DATABASE ANALYSIS END ===")
            }
        }
    }
}
