package com.baka3k.data.movie.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.baka3k.data.movie.database.model.MovieGenreCrossRef

/**
 * Dao fpr cross reference for many to many relationship between [MovieEntity] and [GenreEntity]
 */
@Dao
interface MovieGenreDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnoreMovieGenre(movieGenreEntities: List<MovieGenreCrossRef>): List<Long>
}