package com.msardevelopment.rawggameapp.ui.genredetailsbottomsheet

import androidx.lifecycle.*
import com.msardevelopment.rawggameapp.data.repository.GameRepository
import com.msardevelopment.rawggameapp.data.model.databasemodel.GameDb
import com.msardevelopment.rawggameapp.data.model.databasemodel.GenreDb
import kotlinx.coroutines.*

class GenreDetailsBottomSheetViewModel(private val gameRepository: GameRepository, private val externalId: Int, private val gamesIdsInt: List<Int>) : ViewModel()
{
    var genre: LiveData<GenreDb> = gameRepository.getGenreLiveData(externalId)
    var genreDescription: LiveData<String> = gameRepository.getGenreDescription(externalId)
    var exampleGames: LiveData<List<GameDb>> = gameRepository.getExampleGamesLiveData(gamesIdsInt)

    fun checkGenreDescription(externalId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            gameRepository.checkGenreDescription(externalId)
        }
    }

    fun checkDoGamesExistAndSaveToDatabase() {
        viewModelScope.launch(Dispatchers.IO) {
            gameRepository.checkDoGamesExistAndSaveToDatabase(gamesIdsInt)
        }
    }
}