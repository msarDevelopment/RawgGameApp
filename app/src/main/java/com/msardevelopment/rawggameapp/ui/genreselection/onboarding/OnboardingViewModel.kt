package com.msardevelopment.rawggameapp.ui.genreselection.onboarding

import android.app.Application
import androidx.lifecycle.*
import com.msardevelopment.rawggameapp.data.repository.GameRepository
import com.msardevelopment.rawggameapp.data.database.GameDatabase
import com.msardevelopment.rawggameapp.data.model.databasemodel.GenreDb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OnboardingViewModel(application: Application) : AndroidViewModel(application) {

    private val gameRepository: GameRepository
    var genres: LiveData<List<GenreDb>>

    init {
        val genreDao = GameDatabase.getInstance(application).genreDao
        val gameDao = GameDatabase.getInstance(application).gameDao
        val screenshotDao = GameDatabase.getInstance(application).screenshotDao
        gameRepository = GameRepository(genreDao, gameDao, screenshotDao)
        genres = gameRepository.getGenres()
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
}