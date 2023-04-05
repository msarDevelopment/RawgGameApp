package com.msardevelopment.rawggameapp.data.model.databasemodel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "genres")
data class GenreDb(

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

    @ColumnInfo(name = "games_count")
    var games_count: Int,

    @ColumnInfo(name = "is_selected")
    var is_selected: Boolean,

    @ColumnInfo(name = "example_games")
    var example_games: String,

): Serializable
