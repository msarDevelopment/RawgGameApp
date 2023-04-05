package com.msardevelopment.rawggameapp.data.model.databasemodel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "screenshots")
data class ScreenshotDb(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "link")
    var link: String,

    @ColumnInfo(name = "game_id")
    var game_id: Int
)
