package com.msardevelopment.rawggameapp.data.model.apimodel.gamedetails


import com.google.gson.annotations.SerializedName

data class Platform(
    @SerializedName("platform")
    val platform: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("slug")
    val slug: String
)