package com.msardevelopment.rawggameapp.ui.genredetailsbottomsheet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.msardevelopment.rawggameapp.data.repository.GameRepository

class GenreDetailsViewModelFactory constructor (
    private val gameRepository: GameRepository,
    private val externalId: Int,
    private val gamesIds: List<Int>
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(GenreDetailsBottomSheetViewModel::class.java!!)) {
            GenreDetailsBottomSheetViewModel(this.gameRepository, this.externalId, this.gamesIds) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}