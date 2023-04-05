package com.msardevelopment.rawggameapp.data.model.apimodel.gamesbygenresresponse


import com.google.gson.annotations.SerializedName

data class PlatformX(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("slug")
    val slug: String
)