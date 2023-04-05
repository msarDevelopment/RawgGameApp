package com.msardevelopment.rawggameapp.ui.genreselection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.msardevelopment.rawggameapp.data.repository.GameRepository
import com.msardevelopment.rawggameapp.data.database.GameDatabase
import com.msardevelopment.rawggameapp.data.model.databasemodel.GenreDb
import com.msardevelopment.rawggameapp.databinding.FragmentGenreSelectionBinding
import com.msardevelopment.rawggameapp.ui.GamesByGenreViewModel
import com.msardevelopment.rawggameapp.ui.GamesByGenreViewModelFactory

class GenreSelectionFragment : Fragment() {

    private var _binding: FragmentGenreSelectionBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var gamesByGenreViewModel: GamesByGenreViewModel
    private lateinit var genreSelectionAdapter: GenreSelectionAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentGenreSelectionBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val genreDao = GameDatabase.getInstance(requireContext()).genreDao
        val gameDao = GameDatabase.getInstance(requireContext()).gameDao
        val screenshotDao = GameDatabase.getInstance(requireContext()).screenshotDao
        val gameRepository = GameRepository(genreDao, gameDao, screenshotDao)
        val gamesByGenreViewModelFactory = GamesByGenreViewModelFactory(gameRepository)

        gamesByGenreViewModel =
            ViewModelProvider(requireActivity(), gamesByGenreViewModelFactory).get(GamesByGenreViewModel::class.java)

        setUpGenreSelectionRecyclerView()

        gamesByGenreViewModel.genres.observe(viewLifecycleOwner) {
            if(it != null) {
                genreSelectionAdapter.setData(it)
            }
        }
        gamesByGenreViewModel.fetchGenres()

        binding.ivGenreSelectionBack.setOnClickListener {
            backToDiscoverGamesFragment()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpGenreSelectionRecyclerView() {
        genreSelectionAdapter = GenreSelectionAdapter(
            {externalId -> openGenreDetailsBottomSheet(externalId)},
            {externalId, isSelected -> setGenreSelected(externalId, isSelected)}
        )
        binding.rvGenres.adapter = genreSelectionAdapter
        binding.rvGenres.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun openGenreDetailsBottomSheet(genreDb: GenreDb) {
        //bundle way
        //val bundle = Bundle()
        //bundle.putInt("externalId", genreDb.external_id)
        //bundle.putString("games", genreDb.example_games)
        //findNavController().navigate(R.id.action_genreSelectionFragment_to_genreDetailsBottomSheetFragment, bundle)

        //Safe Args way
        val action = GenreSelectionFragmentDirections.actionGenreSelectionFragmentToGenreDetailsBottomSheetFragment(genreDb.external_id, genreDb.example_games)
        findNavController().navigate(action)
    }

    private fun setGenreSelected(externalId: Int, selected: Boolean) {
        gamesByGenreViewModel.setGenreSelected(externalId, selected)
    }

    private fun backToDiscoverGamesFragment() {
        findNavController().popBackStack()
    }
}