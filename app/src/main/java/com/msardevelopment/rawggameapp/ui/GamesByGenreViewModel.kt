package com.msardevelopment.rawggameapp.ui

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.msardevelopment.rawggameapp.data.repository.GameRepository
import com.msardevelopment.rawggameapp.data.model.apimodel.gamesbygenresresponse.Result
import com.msardevelopment.rawggameapp.data.model.databasemodel.GenreDb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GamesByGenreViewModel(private val gameRepository: GameRepository) : ViewModel() {

    var genres: LiveData<List<GenreDb>> = gameRepository.getGenres()
    var selectedGenresIds: LiveData<List<Int>> = gameRepository.getSelectedGenresExternalIdsLiveData()

    companion object {
        private const val DEFAULT_QUERY = ""
    }
    private val currentQuery = MutableLiveData(DEFAULT_QUERY)
    var gamesByGenres: LiveData<PagingData<Result>> = currentQuery.switchMap { queryString ->
        gameRepository.getGamesByGenrePagedFromQuery(queryString).cachedIn(viewModelScope)
    }

    fun fetchGenres() {
        viewModelScope.launch(Dispatchers.IO) {
            gameRepository.getGenresFromDatabaseOrFetchFromNetworkIfNoneInDatabase()
        }
    }

    fun setGenreSelected(externalId: Int, selected: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            gameRepository.setGenreSelected(externalId, selected)
        }
    }

    fun sendQuery(query: String) {
        currentQuery.value = query
    }
}