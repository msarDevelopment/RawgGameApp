package com.msardevelopment.rawggameapp.data.model.apimodel.genresresponse


import com.google.gson.annotations.SerializedName

data class Game(
    @SerializedName("id")
    val id: Int,
    @SerializedName("slug")
    val slug: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("added")
    val added: Int
)