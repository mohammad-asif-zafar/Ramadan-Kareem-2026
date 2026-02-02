package com.hathway.ramadankareem2026.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hathway.ramadankareem2026.data.local.database.converters.DateConverters
import com.hathway.ramadankareem2026.data.local.database.dao.ZakatCalculationDao
import com.hathway.ramadankareem2026.data.local.database.entity.ZakatCalculationEntity

@Database(
    entities = [BookmarkEntity::class, ZakatCalculationEntity::class],
    version = 3,
    exportSchema = false
)
@TypeConverters(DateConverters::class)
abstract class RamadanDatabase : RoomDatabase() {
    abstract fun bookmarkDao(): BookmarkDao
    abstract fun zakatCalculationDao(): ZakatCalculationDao

    companion object {
        @Volatile
        private var INSTANCE: RamadanDatabase? = null

        fun getDatabase(context: Context): RamadanDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RamadanDatabase::class.java,
                    "ramadan_database"
                )
                    .fallbackToDestructiveMigration(false)
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
