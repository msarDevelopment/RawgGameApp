package com.msardevelopment.rawggameapp.ui.discovergames

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.msardevelopment.rawggameapp.BuildConfig
import com.msardevelopment.rawggameapp.data.api.RawgApi
import com.msardevelopment.rawggameapp.data.model.apimodel.gamesbygenresresponse.GamesByGenresResponse
import com.msardevelopment.rawggameapp.data.model.apimodel.gamesbygenresresponse.ShortScreenshot
import com.msardevelopment.rawggameapp.data.model.databasemodel.ScreenshotDb
import com.msardevelopment.rawggameapp.data.model.databasemodel.dao.ScreenshotDAO
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1

class GamesPagingSource(
    private val rawgApi: RawgApi,
    private val genres: String,
    private val screenshotsDao: ScreenshotDAO
) : PagingSource<Int, com.msardevelopment.rawggameapp.data.model.apimodel.gamesbygenresresponse.Result>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, com.msardevelopment.rawggameapp.data.model.apimodel.gamesbygenresresponse.Result> {
        val position = params.key ?: 1

        return try {
            val response: Response<GamesByGenresResponse>

            if(genres.isEmpty()) {
                response = rawgApi.getAllGames(BuildConfig.API_KEY, position, params.loadSize)
            }
            else {
                response = rawgApi.getGamesByGenres(genres, BuildConfig.API_KEY, position, params.loadSize)
            }

            val games = response.body()!!.results

            val screenshots: MutableList<ScreenshotDb> = mutableListOf()

            for (game: com.msardevelopment.rawggameapp.data.model.apimodel.gamesbygenresresponse.Result in games) {
                for(shortScreenshot: ShortScreenshot in game.shortScreenshots) {
                    val screenshotDb = ScreenshotDb(shortScreenshot.image, game.id)
                    screenshots.add(screenshotDb)
                }
            }
            screenshotsDao.addAllScreenshot(screenshots)

            LoadResult.Page(
                data = games,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (games.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, com.msardevelopment.rawggameapp.data.model.apimodel.gamesbygenresresponse.Result>): Int? {
        //return null
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}