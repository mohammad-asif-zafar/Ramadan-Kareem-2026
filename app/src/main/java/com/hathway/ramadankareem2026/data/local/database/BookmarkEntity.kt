package com.hathway.ramadankareem2026.data.local.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarks")
data class BookmarkEntity(
    @PrimaryKey
    val id: String,
    val itemId: String,
    val itemType: String, // "dua", "quran_ayah", "hadith", etc.
    val title: String,
    val content: String?, // Optional content for quick preview
    val createdAt: Long = System.currentTimeMillis()
)
