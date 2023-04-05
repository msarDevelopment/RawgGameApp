package com.msardevelopment.rawggameapp.data.model.databasemodel.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.msardevelopment.rawggameapp.data.model.databasemodel.GenreDb

@Dao
interface GenreDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addGenre(genreDb: GenreDb)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addAllGenres(genres: List<GenreDb>)

    @Update
    suspend fun updateGenre(genreDb: GenreDb)

    @Delete
    suspend fun deleteGenre(genreDb: GenreDb)

    @Query("SELECT * FROM genres")
    fun getAllGenres(): LiveData<List<GenreDb>>

    @Query("SELECT count(*) FROM genres")
    fun getNumberOfGenres(): Int

    @Query("UPDATE genres SET is_selected = :selected WHERE external_id = :externalId")
    suspend fun setGenreSelected(externalId: Int, selected: Boolean)





    @Query("SELECT * FROM genres WHERE external_id = :externalId")
    fun getGenreLiveData(externalId: Int): LiveData<GenreDb>

    @Query("SELECT * FROM genres WHERE external_id = :externalId")
    fun getGenre(externalId: Int): GenreDb

    @Query("UPDATE genres SET description = :newDescription WHERE external_id = :externalId")
    fun updateGenreDescription(newDescription: String, externalId: Int)

    @Query("SELECT description FROM genres WHERE external_id = :externalId")
    fun getGenreDescriptionLiveData(externalId: Int): LiveData<String>



    @Query("SELECT external_id FROM genres WHERE is_selected = 1")
    fun getSelectedGenresExternalIds(): List<Int>

    @Query("SELECT external_id FROM genres")
    fun getAllGenresExternalIds(): List<Int>



    @Query("SELECT external_id FROM genres WHERE is_selected = 1")
    fun getSelectedGenresExternalIdsLiveData(): LiveData<List<Int>>

}