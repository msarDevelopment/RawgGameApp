package com.msardevelopment.rawggameapp.ui.gamedetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.msardevelopment.rawggameapp.data.repository.GameRepository

class GameDetailsViewModelFactory constructor (
    private val gameRepository: GameRepository,
    private val externalId: Int
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(GameDetailsViewModel::class.java!!)) {
            GameDetailsViewModel(this.gameRepository, this.externalId) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}