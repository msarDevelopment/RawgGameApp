package com.msardevelopment.rawggameapp.data.model.apimodel.gamesbygenresresponse


import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("slug")
    val slug: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("playtime")
    val playtime: Int,
    @SerializedName("platforms")
    val platforms: List<Platform>,
    @SerializedName("stores")
    val stores: List<Store>,
    @SerializedName("released")
    val released: String,
    @SerializedName("tba")
    val tba: Boolean,
    @SerializedName("background_image")
    val backgroundImage: String,
    @SerializedName("rating")
    val rating: Double,
    @SerializedName("rating_top")
    val ratingTop: Int,
    @SerializedName("ratings")
    val ratings: List<Rating>,
    @SerializedName("ratings_count")
    val ratingsCount: Int,
    @SerializedName("reviews_text_count")
    val reviewsTextCount: Int,
    @SerializedName("added")
    val added: Int,
    @SerializedName("added_by_status")
    val addedByStatus: AddedByStatus,
    @SerializedName("metacritic")
    val metacritic: Int?,
    @SerializedName("suggestions_count")
    val suggestionsCount: Int,
    @SerializedName("updated")
    val updated: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("score")
    val score: Any?,
    @SerializedName("clip")
    val clip: Any?,
    @SerializedName("tags")
    val tags: List<Tag>,
    @SerializedName("esrb_rating")
    val esrbRating: EsrbRating,
    @SerializedName("user_game")
    val userGame: Any?,
    @SerializedName("reviews_count")
    val reviewsCount: Int,
    @SerializedName("saturated_color")
    val saturatedColor: String,
    @SerializedName("dominant_color")
    val dominantColor: String,
    @SerializedName("short_screenshots")
    val shortScreenshots: List<ShortScreenshot>,
    @SerializedName("parent_platforms")
    val parentPlatforms: List<ParentPlatform>,
    @SerializedName("genres")
    val genres: List<Genre>
)