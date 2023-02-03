package com.msardevelopment.rawggameapp.data.model.apimodel.gamedetails


import com.google.gson.annotations.SerializedName

data class Store(
    @SerializedName("id")
    val id: Int,
    @SerializedName("url")
    val url: String,
    @SerializedName("store")
    val store: com.msardevelopment.rawggameapp.data.model.apimodel.gamedetails.StoreX
)