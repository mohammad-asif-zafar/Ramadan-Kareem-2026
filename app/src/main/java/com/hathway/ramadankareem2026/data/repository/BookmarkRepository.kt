package com.hathway.ramadankareem2026.data.repository

import com.hathway.ramadankareem2026.data.local.database.BookmarkDao
import com.hathway.ramadankareem2026.data.local.database.BookmarkEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BookmarkRepository @Inject constructor(
    private val bookmarkDao: BookmarkDao
) {
    
    fun getBookmarksByType(itemType: String): Flow<List<BookmarkEntity>> {
        return bookmarkDao.getBookmarksByType(itemType)
    }
    
    suspend fun isBookmarked(itemId: String, itemType: String): Boolean {
        return bookmarkDao.isBookmarked(itemId, itemType)
    }
    
    suspend fun addBookmark(
        itemId: String,
        itemType: String,
        title: String,
        content: String? = null
    ) {
        val bookmark = BookmarkEntity(
            id = "${itemType}_${itemId}",
            itemId = itemId,
            itemType = itemType,
            title = title,
            content = content
        )
        bookmarkDao.insertBookmark(bookmark)
    }
    
    suspend fun removeBookmark(itemId: String, itemType: String) {
        bookmarkDao.deleteBookmarkById(itemId, itemType)
    }
    
    suspend fun toggleBookmark(
        itemId: String,
        itemType: String,
        title: String,
        content: String? = null
    ): Boolean {
        return if (isBookmarked(itemId, itemType)) {
            removeBookmark(itemId, itemType)
            false
        } else {
            addBookmark(itemId, itemType, title, content)
            true
        }
    }
}
