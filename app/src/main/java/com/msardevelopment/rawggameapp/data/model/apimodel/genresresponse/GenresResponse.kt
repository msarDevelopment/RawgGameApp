package com.msardevelopment.rawggameapp.data.model.apimodel.genresresponse


import com.google.gson.annotations.SerializedName

data class GenresResponse(
    @SerializedName("count")
    val count: Int,
    @SerializedName("next")
    val next: Any?,
    @SerializedName("previous")
    val previous: Any?,
    @SerializedName("results")
    val results: List<com.msardevelopment.rawggameapp.data.model.apimodel.genresresponse.Result>
)