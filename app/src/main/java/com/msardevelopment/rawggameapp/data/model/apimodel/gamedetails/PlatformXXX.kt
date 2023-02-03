package com.msardevelopment.rawggameapp.data.model.apimodel.gamedetails


import com.google.gson.annotations.SerializedName

data class PlatformXXX(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("slug")
    val slug: String,
    @SerializedName("image")
    val image: Any?,
    @SerializedName("year_end")
    val yearEnd: Any?,
    @SerializedName("year_start")
    val yearStart: Int?,
    @SerializedName("games_count")
    val gamesCount: Int,
    @SerializedName("image_background")
    val imageBackground: String
)