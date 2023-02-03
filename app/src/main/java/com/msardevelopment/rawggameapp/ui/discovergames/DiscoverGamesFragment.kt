package com.msardevelopment.rawggameapp.ui.discovergames

import android.app.AlertDialog
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.msardevelopment.rawggameapp.data.repository.GameRepository
import com.msardevelopment.rawggameapp.R
import com.msardevelopment.rawggameapp.data.database.GameDatabase
import com.msardevelopment.rawggameapp.databinding.FragmentDiscoverGamesBinding
import com.msardevelopment.rawggameapp.ui.GamesByGenreViewModel
import com.msardevelopment.rawggameapp.ui.GamesByGenreViewModelFactory

class DiscoverGamesFragment : Fragment() {

    private var _binding: FragmentDiscoverGamesBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var gamesByGenreViewModel: GamesByGenreViewModel
    private lateinit var gamesPagedAdapter: GamesPagedAdapter
    private val mRemoteConfig = FirebaseRemoteConfig.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDiscoverGamesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val genreDao = GameDatabase.getInstance(requireContext()).genreDao
        val gameDao = GameDatabase.getInstance(requireContext()).gameDao
        val screenshotDao = GameDatabase.getInstance(requireContext()).screenshotDao
        val gameRepository = GameRepository(genreDao, gameDao, screenshotDao)
        val gamesByGenreViewModelFactory = GamesByGenreViewModelFactory(gameRepository)

        gamesByGenreViewModel =
            ViewModelProvider(requireActivity(), gamesByGenreViewModelFactory).get(GamesByGenreViewModel::class.java)

        gamesByGenreViewModel.selectedGenresIds.observe(viewLifecycleOwner) {
            if (it != null) {
                val query = it.joinToString(",")
                gamesByGenreViewModel.sendQuery(query)
            }
        }

        setUpGamesRecyclerView()

        gamesByGenreViewModel.gamesByGenres.observe(viewLifecycleOwner) {
            it?.let {
                gamesPagedAdapter.submitData(viewLifecycleOwner.lifecycle, it)
            }
        }

        //set to 60 just for demonstration to see changes faster
        val configSettings = FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(60)
            .build()
        mRemoteConfig.setDefaultsAsync(R.xml.remote_config_default)
        mRemoteConfig.setConfigSettingsAsync(configSettings)
        fetchAndActivateConfigManually()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpGamesRecyclerView() {
        gamesPagedAdapter = GamesPagedAdapter(
            {externalId -> openGameDetails(externalId)},
            {title, message -> showBadgeExplanationDialog(title, message)}
        )
        binding.rvGames.adapter = gamesPagedAdapter
        var spanCount = 2
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            spanCount = 3
        }
        binding.rvGames.layoutManager = GridLayoutManager(requireContext(), spanCount)
    }

    fun openGameDetails(gameExternalId: Int) {
        //bundle way
        //val bundle = Bundle()
        //bundle.putInt("gameExternalId", gameExternalId)
        //findNavController().navigate(R.id.action_discoverGamesFragment_to_gameDetailsFragment, bundle)

        //Safe Args way
        val action = DiscoverGamesFragmentDirections.actionDiscoverGamesFragmentToGameDetailsFragment(gameExternalId)
        findNavController().navigate(action)
    }

    private fun showBadgeExplanationDialog(title: String, message: String) {

        val builder = AlertDialog.Builder(requireActivity())
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.dialog_badge_explanation, null)
        builder.setView(dialogLayout)
        val dialog = builder.show()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val tvBadgeExplanationDialogTitle = dialogLayout.findViewById<TextView>(R.id.tvBadgeExplanationDialogTitle)
        tvBadgeExplanationDialogTitle.text = title
        val tvBadgeExplanationDialogMessage = dialogLayout.findViewById<TextView>(R.id.tvBadgeExplanationDialogMessage)
        tvBadgeExplanationDialogMessage.text = message
        val tvDismiss = dialogLayout.findViewById<TextView>(R.id.tvBadgeExplanationDialogDismiss)
        tvDismiss.setOnClickListener {
            dialog.dismiss()
        }
    }

    private fun fetchAndActivateConfigManually() {
        mRemoteConfig.fetch()
            .addOnCompleteListener  { it1 ->
                if(it1.isSuccessful) {
                    mRemoteConfig.activate().addOnCompleteListener { it2 ->
                        if(it2.isSuccessful) {
                            if(mRemoteConfig.getBoolean("show_message")) {
                                binding.ivFirebaseMessage.visibility = View.VISIBLE
                                binding.ivFirebaseMessage.setOnClickListener {
                                    showBadgeExplanationDialog("Firebase Message", mRemoteConfig.getString("message"))
                                }
                            }
                        }
                    }
                }
            }
    }
}