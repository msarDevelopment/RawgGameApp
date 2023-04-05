package com.msardevelopment.rawggameapp.data.model.databasemodel.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.msardevelopment.rawggameapp.data.model.databasemodel.GameDb

@Dao
interface GameDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addGame(gameDb: GameDb)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addAllGames(games: List<GameDb>)

    @Update
    suspend fun updateGame(gameDb: GameDb)

    @Delete
    suspend fun deleteGame(gameDb: GameDb)

    @Query("SELECT EXISTS(SELECT * FROM games WHERE external_id = :externalId)")
    fun doesGameExist(externalId : Int) : Boolean

    @Query("SELECT * FROM games WHERE external_id IN (:externalIds)")
    fun getExampleGamesLiveData(externalIds: List<Int>): LiveData<List<GameDb>>

    @Query("SELECT * FROM games WHERE external_id = :externalId")
    fun getGameLiveData(externalId: Int): LiveData<GameDb>

}