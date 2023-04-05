package com.msardevelopment.rawggameapp.data.model.apimodel.gamedetails


import com.google.gson.annotations.SerializedName

data class Publisher(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("slug")
    val slug: String,
    @SerializedName("games_count")
    val gamesCount: Int,
    @SerializedName("image_background")
    val imageBackground: String
)