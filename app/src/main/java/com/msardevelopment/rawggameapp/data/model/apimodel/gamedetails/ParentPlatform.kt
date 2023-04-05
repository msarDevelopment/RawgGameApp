package com.msardevelopment.rawggameapp.data.model.apimodel.gamedetails


import com.google.gson.annotations.SerializedName

data class ParentPlatform(
    @SerializedName("platform")
    val platform: com.msardevelopment.rawggameapp.data.model.apimodel.gamedetails.PlatformX
)