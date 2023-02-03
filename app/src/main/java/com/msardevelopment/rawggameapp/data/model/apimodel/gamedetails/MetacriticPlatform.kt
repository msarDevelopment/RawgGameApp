package com.msardevelopment.rawggameapp.data.model.apimodel.gamedetails


import com.google.gson.annotations.SerializedName

data class MetacriticPlatform(
    @SerializedName("metascore")
    val metascore: Int,
    @SerializedName("url")
    val url: String,
    @SerializedName("platform")
    val platform: com.msardevelopment.rawggameapp.data.model.apimodel.gamedetails.Platform
)