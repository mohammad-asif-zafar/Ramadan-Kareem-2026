package com.hathway.ramadankareem2026.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hathway.ramadankareem2026.data.local.database.BookmarkDao
import com.hathway.ramadankareem2026.data.local.database.BookmarkEntity

object BookmarkManager {
    private var database: BookmarkDatabase? = null
    
    fun getDatabase(context: Context): BookmarkDatabase {
        return database ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                BookmarkDatabase::class.java,
                "bookmark_database"
            ).fallbackToDestructiveMigration().build()
            database = instance
            instance
        }
    }
}

@Database(
    entities = [BookmarkEntity::class],
    version = 1,
    exportSchema = false
)
abstract class BookmarkDatabase : RoomDatabase() {
    abstract fun bookmarkDao(): BookmarkDao
}
