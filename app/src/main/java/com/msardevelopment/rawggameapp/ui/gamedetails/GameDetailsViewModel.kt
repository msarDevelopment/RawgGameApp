package com.msardevelopment.rawggameapp.ui.gamedetails

import androidx.lifecycle.*
import com.msardevelopment.rawggameapp.data.repository.GameRepository
import com.msardevelopment.rawggameapp.data.model.databasemodel.GameDb
import com.msardevelopment.rawggameapp.data.model.databasemodel.ScreenshotDb
import kotlinx.coroutines.*

class GameDetailsViewModel(private val gameRepository: GameRepository, private val externalId: Int) : ViewModel()
{
    var game: LiveData<GameDb> = gameRepository.getGameLiveData(externalId)
    var screenshots: LiveData<List<ScreenshotDb>> = gameRepository.getScreenshotsLiveData(externalId)

    fun checkDoesGameExistAndSaveToDatabase() {
        viewModelScope.launch(Dispatchers.IO) {
            gameRepository.checkDoesGameExistAndSaveToDatabase(externalId)
        }
    }
}