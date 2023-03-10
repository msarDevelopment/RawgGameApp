package com.msardevelopment.rawggameapp.data.model.apimodel.genredetails


import com.google.gson.annotations.SerializedName

data class GenreDetailsResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("slug")
    val slug: String,
    @SerializedName("games_count")
    val gamesCount: Int,
    @SerializedName("image_background")
    val imageBackground: String,
    @SerializedName("description")
    val description: String
)