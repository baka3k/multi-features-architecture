package com.baka3k.data.movie.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity


/**
 * Cross reference for many to many relationship between [MovieEntity] and [GenreEntity]
 */
@Entity(
    tableName = "movie_genre_cross_ref",
    primaryKeys = ["movie_id", "genre_id"],
//    foreignKeys = [
//        ForeignKey(
//            entity = MovieEntity::class,
//            parentColumns = ["id"],
//            childColumns = ["movie_id"],
//            onDelete = ForeignKey.CASCADE
//        ),
//        ForeignKey(
//            entity = GenreEntity::class,
//            parentColumns = ["id"],
//            childColumns = ["genre_id"],
//            onDelete = ForeignKey.CASCADE
//        ),
//    ],
//    indices = [
//        Index(value = ["movie_id"]),
//        Index(value = ["genre_id"]),
//    ],
)
data class MovieGenreCrossRef(
    @ColumnInfo(name = "movie_id")
    val movieId: Long,
    @ColumnInfo(name = "genre_id")
    val genreId: Long,
)
