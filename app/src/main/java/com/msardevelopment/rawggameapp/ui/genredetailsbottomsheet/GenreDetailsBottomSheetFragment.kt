package com.msardevelopment.rawggameapp.ui.genredetailsbottomsheet

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.msardevelopment.rawggameapp.data.database.GameDatabase
import com.msardevelopment.rawggameapp.data.repository.GameRepository
import com.msardevelopment.rawggameapp.databinding.FragmentGenreDetailsBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class GenreDetailsBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentGenreDetailsBottomSheetBinding? = null
    private val binding get() = _binding!!
    private lateinit var genreDetailsBottomSheetViewModel: GenreDetailsBottomSheetViewModel
    private lateinit var exampleGamesAdapter: GenreDetailsExampleGamesAdapter
    private val args: GenreDetailsBottomSheetFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentGenreDetailsBottomSheetBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //bundle way
        //val externalId = arguments?.getInt("externalId")
        //val games = arguments?.getString("games")

        //Safe Args way
        val externalId = args.externalId
        val games = args.games

        val gamesIdsString: List<String>? = games?.split(",")?.toList()
        val gamesIdsInt: List<Int> ?= gamesIdsString?.map {int -> int.toInt() }?.toList()

        val genreDao = GameDatabase.getInstance(requireContext()).genreDao
        val gameDao = GameDatabase.getInstance(requireContext()).gameDao
        val screenshotDao = GameDatabase.getInstance(requireContext()).screenshotDao
        val gameRepository = GameRepository(genreDao, gameDao, screenshotDao)
        val feedViewModelFactory = GenreDetailsViewModelFactory(gameRepository, externalId!!, gamesIdsInt!!)
        genreDetailsBottomSheetViewModel =
            ViewModelProvider(this, feedViewModelFactory).get(GenreDetailsBottomSheetViewModel::class.java)

        setUpExampleGamesRecyclerView()

        genreDetailsBottomSheetViewModel.genre.observe(viewLifecycleOwner) {
            if(it != null) {
                binding.tvGenreDetailsTitle.text = it.name.lowercase()
                binding.tvGenreDetailsNumberOfGames.text = it.games_count.toString()
            }
        }

        genreDetailsBottomSheetViewModel.genreDescription.observe(viewLifecycleOwner) {
            binding.tvGenreDetailsDescription.text = it
        }

        genreDetailsBottomSheetViewModel.checkGenreDescription(externalId)

        genreDetailsBottomSheetViewModel.exampleGames.observe(viewLifecycleOwner){
            if (it.isNotEmpty()) {
                exampleGamesAdapter.setData(it)
            }
        }

        genreDetailsBottomSheetViewModel.checkDoGamesExistAndSaveToDatabase()

        return root
    }

    //to set transparency of dialog background (to avoid having background behind rounded corners)
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            setOnShowListener {
                val bottomSheet = findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
                bottomSheet.setBackgroundResource(android.R.color.transparent)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpExampleGamesRecyclerView() {
        exampleGamesAdapter = GenreDetailsExampleGamesAdapter()
        binding.rvExampleGames.adapter = exampleGamesAdapter
        binding.rvExampleGames.layoutManager = GridLayoutManager(requireContext(), 3)
    }
}