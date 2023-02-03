package com.msardevelopment.rawggameapp.data.model.databasemodel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "games")
data class GameDb(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int?,

    @ColumnInfo(name = "external_id")
    var external_id: Int,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "slug")
    var slug: String,

    @ColumnInfo(name = "description")
    var description: String,

    @ColumnInfo(name = "metacritic")
    var metacritic: Int,

    @ColumnInfo(name = "released")
    var released: String,

    @ColumnInfo(name = "tba")
    var tba: Boolean,

    @ColumnInfo(name = "background_image")
    var background_image: String,

    @ColumnInfo(name = "background_image_additional")
    var background_image_additional: String?,

    @ColumnInfo(name = "website")
    var website: String,

    @ColumnInfo(name = "owned")
    var owned: Int,

    @ColumnInfo(name = "beaten")
    var beaten: Int,

    @ColumnInfo(name = "playing")
    var playing: Int,

    @ColumnInfo(name = "reddit_url")
    var reddit_url: String,

    @ColumnInfo(name = "platforms")
    var platforms: String,

    @ColumnInfo(name = "developers")
    var developers: String,

    @ColumnInfo(name = "genres")
    var genres: String,

    @ColumnInfo(name = "publishers")
    var publishers: String,

    @ColumnInfo(name = "esrb_rating")
    var esrb_rating: String?

): Serializable
