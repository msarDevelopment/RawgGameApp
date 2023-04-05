package com.msardevelopment.rawggameapp.data.model.apimodel.gamesbygenresresponse


import com.google.gson.annotations.SerializedName

data class Platform(
    @SerializedName("platform")
    val platform: PlatformX
)