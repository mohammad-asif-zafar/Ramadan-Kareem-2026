package com.hathway.ramadankareem2026.data.local.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarkDao {
    
    @Query("SELECT * FROM bookmarks WHERE itemType = :itemType ORDER BY createdAt DESC")
    fun getBookmarksByType(itemType: String): Flow<List<BookmarkEntity>>
    
    @Query("SELECT * FROM bookmarks WHERE itemId = :itemId AND itemType = :itemType LIMIT 1")
    suspend fun getBookmark(itemId: String, itemType: String): BookmarkEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(bookmark: BookmarkEntity)
    
    @Delete
    suspend fun deleteBookmark(bookmark: BookmarkEntity)
    
    @Query("DELETE FROM bookmarks WHERE itemId = :itemId AND itemType = :itemType")
    suspend fun deleteBookmarkById(itemId: String, itemType: String)
    
    @Query("SELECT COUNT(*) > 0 FROM bookmarks WHERE itemId = :itemId AND itemType = :itemType")
    suspend fun isBookmarked(itemId: String, itemType: String): Boolean
}
