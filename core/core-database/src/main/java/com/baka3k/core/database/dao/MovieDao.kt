package com.baka3k.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.baka3k.core.database.model.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    companion object{
        const val POPULAR = 1
        const val NOW_PLAYING = 2
        const val TOP_RATE = 3
        const val UP_COMMING = 4
    }
    @Query(value = "SELECT * FROM movies WHERE type = '$POPULAR'")
    fun getPolularMovieEntitiesStream(): Flow<List<MovieEntity>>

    @Query(value = "SELECT * FROM movies WHERE type = '$TOP_RATE'")
    fun getTopRateMovieEntitiesStream(): Flow<List<MovieEntity>>

    @Query(value = "SELECT * FROM movies WHERE type = '$UP_COMMING'")
    fun getUpCommingMovieEntitiesStream(): Flow<List<MovieEntity>>

    @Query(value = "SELECT * FROM movies WHERE type = '$NOW_PLAYING'")
    fun getNowPlayingEntitiesStream(): Flow<List<MovieEntity>>

    @Query(
        value = """
        SELECT * FROM movies
        WHERE id = :id
    """
    )
    fun getMovieEntity(id: String): Flow<MovieEntity>
    @Query(value = "SELECT * FROM movies")
    fun getMovieEntitiesStream(): Flow<List<MovieEntity>>
    @Query(
        value = """
        SELECT * FROM movies
        WHERE id IN (:ids)
    """
    )
    fun getMovieEntitiesStream(ids: Set<String>): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnoreMovie(movieEntities: List<MovieEntity>): List<Long>
    @Insert
    suspend fun insertOrIgnoreMovie1(movieEntities: List<MovieEntity>): List<Long>
    @Update
    suspend fun updateMovies(entities: List<MovieEntity>)

    /**
     * Inserts or updates [entities] in the db under the specified primary keys
     */
    @Transaction
    suspend fun upsertMovie(entities: List<MovieEntity>) = upsert(
        items = entities,
        insertMany = ::insertOrIgnoreMovie,
        updateMany = ::updateMovies
    )

    /**
     * Deletes rows in the db matching the specified [ids]
     */
    @Query(
        value = """
            DELETE FROM movies
            WHERE id in (:ids)
        """
    )
    suspend fun deleteMovie(ids: List<String>)
    @Query(
        value = """
            SELECT COUNT(*) FROM movies

        """
    )
    suspend fun getCount(): Int
}