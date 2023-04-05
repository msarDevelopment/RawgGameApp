package com.msardevelopment.rawggameapp.data.repository

import androidx.core.text.HtmlCompat
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.msardevelopment.rawggameapp.BuildConfig
import com.msardevelopment.rawggameapp.data.api.Retrofit
import com.msardevelopment.rawggameapp.data.model.apimodel.genresresponse.Game
import com.msardevelopment.rawggameapp.data.model.databasemodel.GameDb
import com.msardevelopment.rawggameapp.data.model.databasemodel.GenreDb
import com.msardevelopment.rawggameapp.data.model.databasemodel.ScreenshotDb
import com.msardevelopment.rawggameapp.data.model.databasemodel.dao.GameDAO
import com.msardevelopment.rawggameapp.data.model.databasemodel.dao.GenreDAO
import com.msardevelopment.rawggameapp.data.model.databasemodel.dao.ScreenshotDAO
import com.msardevelopment.rawggameapp.ui.discovergames.GamesPagingSource

class GameRepository(
    private val genreDao: GenreDAO,
    private val gameDao: GameDAO,
    private val screenshotDAO: ScreenshotDAO) {

    private val retrofit = Retrofit()
    private val api = retrofit.api

    //OnboardingViewModel and GamesByGenresViewModel
    fun getGenres(): LiveData<List<GenreDb>> {
        val genres = genreDao.getAllGenres()
        return genres
    }

    //OnboardingViewModel and GamesByGenresViewModel
    suspend fun getGenresFromDatabaseOrFetchFromNetworkIfNoneInDatabase() {
        val numberOfGenres = genreDao.getNumberOfGenres()
        if(numberOfGenres == 0) {
            val response = api.getGenres(BuildConfig.API_KEY)
            val genres: MutableList<GenreDb> = mutableListOf()

            if (response != null) {
                for (result: com.msardevelopment.rawggameapp.data.model.apimodel.genresresponse.Result in response.body()?.results!!) {
                    var gameIds = ""
                    for(game: Game in result.games) {
                        gameIds += game.id
                        gameIds += ","
                    }
                    val genreToSave = GenreDb(null, result.id, result.name, result.slug, "", result.gamesCount, false, gameIds.dropLast(1))
                    genres.add(genreToSave)
                }
                genreDao.addAllGenres(genres)
            }
        }
    }

    //OnboardingViewModel and GamesByGenresViewModel
    suspend fun setGenreSelected(externalId: Int, selected: Boolean) {
        genreDao.setGenreSelected(externalId, selected)
    }

    //GamesByGenreViewModel
    fun getSelectedGenresExternalIdsLiveData(): LiveData<List<Int>> {
        val genresExternalIdsList = genreDao.getSelectedGenresExternalIdsLiveData()
        return genresExternalIdsList
    }

    //GamesByGenreViewModel
    fun getGamesByGenrePagedFromQuery(query: String): LiveData<PagingData<com.msardevelopment.rawggameapp.data.model.apimodel.gamesbygenresresponse.Result>> {
        val pager = Pager(
            config = PagingConfig(
                pageSize = 40,
                maxSize = 150,
                enablePlaceholders = true
            ),
            pagingSourceFactory = { GamesPagingSource(api, query, screenshotDAO) }
        ).liveData

        return pager
    }



    //GenreDetailsBottomSheetViewModel
    fun getGenreLiveData(externalId: Int): LiveData<GenreDb> {
        val genre = genreDao.getGenreLiveData(externalId)
        return genre
    }

    //GenreDetailsBottomSheetViewModel
    fun getGenreDescription(externalId: Int): LiveData<String> {
        val genreDescription = genreDao.getGenreDescriptionLiveData(externalId)
        return genreDescription
    }

    //GenreDetailsBottomSheetViewModel
    fun getExampleGamesLiveData(externalIds: List<Int>): LiveData<List<GameDb>> {
        val games = gameDao.getExampleGamesLiveData(externalIds)
        return games
    }

    //GenreDetailsBottomSheetViewModel
    suspend fun checkGenreDescription(externalId: Int): GenreDb {
        val genre = genreDao.getGenre(externalId)
        if(genre.description == "") {
            updateGenreDescription(externalId)
        }
        return genre
    }

    //GenreDetailsBottomSheetViewModel (GameRepository.checkGenreDescription())
    suspend fun updateGenreDescription(externalId: Int) {
        val response = api.getGenreDetails(externalId, BuildConfig.API_KEY)
        var description = ""
        if(response != null) {
            description = response.body()?.description.toString()
            description = HtmlCompat.fromHtml(description, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
        }
        genreDao.updateGenreDescription(description, externalId)
    }

    //GenreDetailsBottomSheetViewModel
    suspend fun checkDoGamesExistAndSaveToDatabase(gameIds: List<Int>) {
        for (externalId: Int in gameIds) {
            if(!gameDao.doesGameExist(externalId)) {
                fetchGameAndSaveToDatabase(externalId)
            }
        }
    }



    //called when getting games for genre details example games and when opening game details
    //GenreDetailsBottomSheetViewModel uses it in GameRepository.checkDoGamesExistAndSaveToDatabase()
    //in a for loop of external ids of Games
    //GameDetailsViewModel uses it in GameRepository.checkDoesGameExistAndSaveToDatabase() on a
    //single external id
    private suspend fun fetchGameAndSaveToDatabase(externalId: Int) {
        val response = api.getGameDetails(externalId, BuildConfig.API_KEY)

        if(response != null) {
            val game = response.body()!!
            val platformsList = game.platforms.map { it.platform.name }
            val developersList = game.developers.map { it.name }
            val genresList = game.genres.map { it.name }
            val publishersList = game.publishers.map { it.name }
            val platforms = platformsList.joinToString(", ")
            val developers = developersList.joinToString(", ")
            val genres = genresList.joinToString(", ")
            val publishers = publishersList.joinToString(", ")

            val gameToSave = GameDb(
                null,
                game.id,
                game.name,
                game.slug,
                game.descriptionRaw,
                game.metacritic,
                game.released,
                game.tba,
                game.backgroundImage,
                game.backgroundImageAdditional,
                game.website,
                game.addedByStatus.owned,
                game.addedByStatus.beaten,
                game.addedByStatus.playing,
                game.redditUrl,
                platforms,
                developers,
                genres,
                publishers,
                game.esrbRating?.name ?: ""
            )
            gameDao.addGame(gameToSave)
        }
    }



    //GameDetailsViewModel
    fun getGameLiveData(externalId: Int): LiveData<GameDb> {
        val genre = gameDao.getGameLiveData(externalId)
        return genre
    }

    //GameDetailsViewModel
    fun getScreenshotsLiveData(gameId: Int): LiveData<List<ScreenshotDb>> {
        val screenshots = screenshotDAO.getScreenshotsLiveData(gameId)
        return screenshots
    }

    //GameDetailsViewModel
    suspend fun checkDoesGameExistAndSaveToDatabase(externalId: Int) {
        if(!gameDao.doesGameExist(externalId)) {
            fetchGameAndSaveToDatabase(externalId)
        }
    }
}