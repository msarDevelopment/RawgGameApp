package com.msardevelopment.rawggameapp.data.model.apimodel.gamesbygenresresponse


import com.google.gson.annotations.SerializedName

data class Rating(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("count")
    val count: Int,
    @SerializedName("percent")
    val percent: Double
)