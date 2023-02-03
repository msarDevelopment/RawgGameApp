package com.msardevelopment.rawggameapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.msardevelopment.rawggameapp.data.model.databasemodel.GenreDb
import com.msardevelopment.rawggameapp.data.model.databasemodel.GameDb
import com.msardevelopment.rawggameapp.data.model.databasemodel.ScreenshotDb
import com.msardevelopment.rawggameapp.data.model.databasemodel.dao.GameDAO
import com.msardevelopment.rawggameapp.data.model.databasemodel.dao.GenreDAO
import com.msardevelopment.rawggameapp.data.model.databasemodel.dao.ScreenshotDAO

@Database(
    entities = [
        GenreDb::class,
        GameDb::class,
        ScreenshotDb::class
    ],
    version = 1,
    exportSchema = false
)
abstract class GameDatabase : RoomDatabase() {

    abstract val genreDao: GenreDAO
    abstract val gameDao: GameDAO
    abstract val screenshotDao: ScreenshotDAO

    companion object {
        @Volatile
        private var INSTANCE: GameDatabase? = null
        fun getInstance(context: Context): GameDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        GameDatabase::class.java,
                        "game_database"
                    ).build()
                }
                return instance
            }
        }

    }

}