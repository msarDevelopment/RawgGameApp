package com.msardevelopment.rawggameapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.msardevelopment.rawggameapp.data.repository.GameRepository

class GamesByGenreViewModelFactory constructor (
    private val gameRepository: GameRepository
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(GamesByGenreViewModel::class.java!!)) {
            GamesByGenreViewModel(this.gameRepository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}