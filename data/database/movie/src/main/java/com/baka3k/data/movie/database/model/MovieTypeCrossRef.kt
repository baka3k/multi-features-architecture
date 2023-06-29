package com.baka3k.data.movie.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index

@Entity(
    tableName = "movie_type_cross_ref",
    primaryKeys = ["movie_id", "type_id"],
//    foreignKeys = [
//        ForeignKey(
//            entity = MovieEntity::class,
//            parentColumns = ["id"],
//            childColumns = ["movie_id"],
//            onDelete = ForeignKey.CASCADE
//        ),
//        ForeignKey(
//            entity = TypeEntity::class,
//            parentColumns = ["id"],
//            childColumns = ["type_id"],
//            onDelete = ForeignKey.CASCADE
//        ),
//    ],
    indices = [
        Index(value = ["movie_id"]),
        Index(value = ["type_id"]),
    ],
)
data class MovieTypeCrossRef(
    @ColumnInfo(name = "movie_id")
    val movieId: Long,
    @ColumnInfo(name = "type_id")
    val typeId: Long,
)
