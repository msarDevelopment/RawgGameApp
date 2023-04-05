package com.msardevelopment.rawggameapp.data.api

import com.msardevelopment.rawggameapp.data.model.apimodel.gamedetails.GameDetailsResponse
import com.msardevelopment.rawggameapp.data.model.apimodel.gamesbygenresresponse.GamesByGenresResponse
import com.msardevelopment.rawggameapp.data.model.apimodel.genredetails.GenreDetailsResponse
import com.msardevelopment.rawggameapp.data.model.apimodel.genresresponse.GenresResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RawgApi {

    @GET("genres")
    suspend fun getGenres(@Query("key") key: String): Response<GenresResponse>

    @GET("genres/{id}")
    suspend fun getGenreDetails(@Path("id") id: Int, @Query("key") key: String): Response<GenreDetailsResponse>

    @GET("games")
    suspend fun getGamesByGenres(@Query("genres") genres: String, @Query("key") key: String, @Query("page") page: Int, @Query("page_size") page_size: Int): Response<GamesByGenresResponse>

    @GET("games")
    suspend fun getAllGames(@Query("key") key: String, @Query("page") page: Int, @Query("page_size") page_size: Int): Response<GamesByGenresResponse>

    @GET("games/{id}")
    suspend fun getGameDetails(@Path("id") id: Int, @Query("key") key: String): Response<GameDetailsResponse>

}