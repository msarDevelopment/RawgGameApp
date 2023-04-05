package com.msardevelopment.rawggameapp.data.model.databasemodel.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.msardevelopment.rawggameapp.data.model.databasemodel.ScreenshotDb

@Dao
interface ScreenshotDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addScreenshot(screenshotDb: ScreenshotDb)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addAllScreenshot(games: List<ScreenshotDb>)

    @Update
    suspend fun updateScreenshot(screenshotDb: ScreenshotDb)

    @Delete
    suspend fun deleteScreenshot(screenshotDb: ScreenshotDb)

    @Query("SELECT * FROM screenshots WHERE game_id = :gameId")
    fun getScreenshotsLiveData(gameId: Int): LiveData<List<ScreenshotDb>>

}