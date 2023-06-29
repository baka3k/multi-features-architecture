package com.baka3k.data.movie.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.baka3k.data.movie.database.model.MovieTypeCrossRef


/**
 * Dao fpr cross reference for many to many relationship between [MovieEntity] and [TypeEntity]
 */
@Dao
interface MovieTypeDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnoreMovieType(movieTypeEntities: List<MovieTypeCrossRef>): List<Long>

    @Query(
        value = """
            SELECT COUNT(*) FROM movie_type_cross_ref

        """
    )
    suspend fun getCount(): Int
}
