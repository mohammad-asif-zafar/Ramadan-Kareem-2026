package com.hathway.ramadankareem2026.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.DatabaseConfiguration
import androidx.room.InvalidationTracker
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.hathway.ramadankareem2026.data.local.database.BookmarkDao
import com.hathway.ramadankareem2026.data.local.database.BookmarkEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

object BookmarkManager {
    private var database: BookmarkDatabase? = null
    private var appContext: Context? = null

    fun init(context: Context) {
        appContext = context.applicationContext
    }

    fun getDatabase(context: Context? = null): BookmarkDatabase {
        return database ?: synchronized(this) {
            database ?: try {
                val ctx = context?.applicationContext ?: appContext ?: throw IllegalStateException("No context available")
                Room.databaseBuilder(
                    ctx, BookmarkDatabase::class.java, "bookmark_database"
                ).fallbackToDestructiveMigration(false).build().also { database = it }
            } catch (e: Exception) {
                android.util.Log.w("BookmarkManager", "Using DummyBookmarkDatabase for preview/unattached context", e)
                DummyBookmarkDatabase().also { database = it }
            }
        }
    }
}

class DummyBookmarkDao : BookmarkDao {
    override fun getBookmarksByType(itemType: String): Flow<List<BookmarkEntity>> = flowOf(emptyList())
    override suspend fun getBookmark(itemId: String, itemType: String): BookmarkEntity? = null
    override suspend fun insertBookmark(bookmark: BookmarkEntity) {}
    override suspend fun deleteBookmark(bookmark: BookmarkEntity) {}
    override suspend fun deleteBookmarkById(itemId: String, itemType: String) {}
    override suspend fun isBookmarked(itemId: String, itemType: String): Boolean = false
    override suspend fun getTotalBookmarkCount(): Int = 0
    override suspend fun getMosqueBookmarkCount(): Int = 0
    override suspend fun getDuaBookmarkCount(): Int = 0
    override suspend fun getAllahNameBookmarkCount(): Int = 0
    override suspend fun getQuranBookmarkCount(): Int = 0
    override suspend fun getAllBookmarks(): List<BookmarkEntity> = emptyList()
}

class DummyBookmarkDatabase : BookmarkDatabase() {
    private val dao = DummyBookmarkDao()
    override fun bookmarkDao(): BookmarkDao = dao
    @Suppress("DEPRECATION")
    override fun createOpenHelper(config: DatabaseConfiguration): SupportSQLiteOpenHelper {
        return object : SupportSQLiteOpenHelper {
            override val databaseName: String = "dummy"
            override val writableDatabase: SupportSQLiteDatabase
                get() = throw UnsupportedOperationException("Dummy database")
            override val readableDatabase: SupportSQLiteDatabase
                get() = throw UnsupportedOperationException("Dummy database")
            override fun setWriteAheadLoggingEnabled(enabled: Boolean) {}
            override fun close() {}
        }
    }
    override fun createInvalidationTracker(): InvalidationTracker {
        return InvalidationTracker(this, emptyMap(), emptyMap(), "bookmarks")
    }
    override fun clearAllTables() {}
}

@Database(
    entities = [BookmarkEntity::class], version = 1, exportSchema = false
)
abstract class BookmarkDatabase : RoomDatabase() {
    abstract fun bookmarkDao(): BookmarkDao
}
