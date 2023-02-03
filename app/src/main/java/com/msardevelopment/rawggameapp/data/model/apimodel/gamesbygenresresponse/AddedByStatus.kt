package com.msardevelopment.rawggameapp.data.model.apimodel.gamesbygenresresponse


import com.google.gson.annotations.SerializedName

data class AddedByStatus(
    @SerializedName("yet")
    val yet: Int,
    @SerializedName("owned")
    val owned: Int,
    @SerializedName("beaten")
    val beaten: Int,
    @SerializedName("toplay")
    val toplay: Int,
    @SerializedName("dropped")
    val dropped: Int,
    @SerializedName("playing")
    val playing: Int
)