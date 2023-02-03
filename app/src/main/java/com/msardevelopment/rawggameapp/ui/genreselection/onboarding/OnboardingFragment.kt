package com.msardevelopment.rawggameapp.ui.genreselection.onboarding

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.msardevelopment.rawggameapp.Constants
import com.msardevelopment.rawggameapp.R
import com.msardevelopment.rawggameapp.data.model.databasemodel.GenreDb
import com.msardevelopment.rawggameapp.databinding.FragmentOnboardingBinding
import com.msardevelopment.rawggameapp.ui.genreselection.GenreSelectionAdapter

class OnboardingFragment : Fragment() {

    private var _binding: FragmentOnboardingBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var onboardingViewModel: OnboardingViewModel
    private lateinit var sharedPref: SharedPreferences
    private lateinit var editSharedPref: SharedPreferences.Editor
    private lateinit var genreSelectionAdapter: GenreSelectionAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnboardingBinding.inflate(inflater, container, false)
        val root: View = binding.root

        onboardingViewModel =
            ViewModelProvider(this).get(OnboardingViewModel::class.java)

        sharedPref = requireActivity().getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
        editSharedPref = sharedPref.edit()

        setUpGenreSelectionRecyclerView()

        onboardingViewModel.genres.observe(viewLifecycleOwner) {
            if(it != null) {
                genreSelectionAdapter.setData(it)
            }
        }
        onboardingViewModel.fetchGenres()

        binding.genreSelectionFab.setOnClickListener {
            editSharedPref.putBoolean(Constants.ONBOARDING_DONE, true)
            editSharedPref.commit()
            openHomeScreen()
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
        //findNavController().navigate(R.id.action_onboardingFragment_to_genreDetailsBottomSheetFragment, bundle)

        //Safe Args way
        val action = OnboardingFragmentDirections.actionOnboardingFragmentToGenreDetailsBottomSheetFragment(genreDb.external_id, genreDb.example_games)
        findNavController().navigate(action)
    }

    private fun setGenreSelected(externalId: Int, selected: Boolean) {
        onboardingViewModel.setGenreSelected(externalId, selected)
    }

    private fun openHomeScreen() {
        findNavController().navigate(R.id.action_onboardingFragment_to_discoverGamesFragment)
    }
}