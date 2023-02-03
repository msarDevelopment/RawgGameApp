package com.msardevelopment.rawggameapp.data.model.apimodel.gamedetails


import com.google.gson.annotations.SerializedName

data class PlatformXX(
    @SerializedName("platform")
    val platform: com.msardevelopment.rawggameapp.data.model.apimodel.gamedetails.PlatformXXX,
    @SerializedName("released_at")
    val releasedAt: String,
    @SerializedName("requirements")
    val requirements: com.msardevelopment.rawggameapp.data.model.apimodel.gamedetails.Requirements?
)