package com.baka3k.core.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.baka3k.core.database.dao.MovieDao
import com.baka3k.core.database.model.MovieEntity
import com.baka3k.core.database.util.InstantConverter
import com.baka3k.core.database.util.NewsResourceTypeConverter

@Database(
    entities = [
        MovieEntity::class,
    ],
    version = 9,
    autoMigrations = [
        AutoMigration(from = 1, to = 2),
//        AutoMigration(from = 2, to = 3, spec = DatabaseMigrations.Schema2to3::class),
//        AutoMigration(from = 3, to = 4),
//        AutoMigration(from = 4, to = 5),
//        AutoMigration(from = 5, to = 6),
//        AutoMigration(from = 6, to = 7),
//        AutoMigration(from = 7, to = 8),
//        AutoMigration(from = 8, to = 9),
    ],
    exportSchema = true,
)
@TypeConverters(
    InstantConverter::class,
    NewsResourceTypeConverter::class,
)
abstract class HiDatabase:RoomDatabase() {
    abstract fun movieDao(): MovieDao
}