package com.msardevelopment.rawggameapp.ui.gamedetails

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.msardevelopment.rawggameapp.R
import com.msardevelopment.rawggameapp.data.database.GameDatabase
import com.msardevelopment.rawggameapp.data.repository.GameRepository
import com.msardevelopment.rawggameapp.databinding.FragmentGameDetailsBinding

class GameDetailsFragment : DialogFragment() {

    private var _binding: FragmentGameDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var gameDetailsViewModel: GameDetailsViewModel
    private lateinit var gameDetailsScreenshotAdapter: GameDetailsScreenshotAdapter
    private val args: GameDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentGameDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val genreDao = GameDatabase.getInstance(requireContext()).genreDao
        val gameDao = GameDatabase.getInstance(requireContext()).gameDao
        val screenshotDao = GameDatabase.getInstance(requireContext()).screenshotDao
        val gameRepository = GameRepository(genreDao, gameDao, screenshotDao)

        //bundle way
        //var externalId = arguments?.getInt("gameExternalId")

        //Safe Args way
        val externalId = args.gameExternalId

        val feedViewModelFactory = GameDetailsViewModelFactory(gameRepository, externalId!!)

        gameDetailsViewModel =
            ViewModelProvider(this, feedViewModelFactory).get(GameDetailsViewModel::class.java)


        gameDetailsViewModel.game.observe(viewLifecycleOwner) {
            if(it != null) {
                binding.ivGameDetailsImage.load(it.background_image)

                val owned = it.owned
                val playing = it.playing
                val beaten = it.beaten
                val playingRatio = playing.toDouble() / owned
                val beatenRatio = beaten.toDouble() / owned
                binding.ivGameDetailsHot.visibility = if (playingRatio > 0.04) View.VISIBLE else View.GONE
                binding.ivGameDetailsChallenging.visibility = if (beatenRatio < 0.03) View.VISIBLE else View.GONE

                when (it.metacritic) {
                    in 75..100 -> {
                        binding.tvGameDetailsMetacritic.setBackgroundResource(R.drawable.bg_rounded_score_high)
                        binding.tvGameDetailsMetacritic.setTextColor(getResources().getColor(R.color.black))
                        binding.tvGameDetailsMetacritic.text = it.metacritic.toString()
                    }
                    in 50..74 -> {
                        binding.tvGameDetailsMetacritic.setBackgroundResource(R.drawable.bg_rounded_score_mid)
                        binding.tvGameDetailsMetacritic.setTextColor(getResources().getColor(R.color.black))
                        binding.tvGameDetailsMetacritic.text = it.metacritic.toString()
                    }
                    in 1..49 -> {
                        binding.tvGameDetailsMetacritic.setBackgroundResource(R.drawable.bg_rounded_score_low)
                        binding.tvGameDetailsMetacritic.setTextColor(getResources().getColor(R.color.white))
                        binding.tvGameDetailsMetacritic.text = it.metacritic.toString()
                    }
                    else -> {
                        binding.tvGameDetailsMetacritic.setBackgroundResource(R.drawable.bg_rounded_score_no_score)
                        binding.tvGameDetailsMetacritic.setTextColor(getResources().getColor(R.color.white))
                        binding.tvGameDetailsMetacritic.text = resources.getString(R.string.game_details_metacritic_no_score)
                    }
                }

                binding.tvGameDetailsTitle.text = it.name.lowercase()
                binding.tvGameDetailsDescription.text = it.description

                val listOfChips = listOf(binding.tvGameDetailsGenre1, binding.tvGameDetailsGenre2, binding.tvGameDetailsGenre3)
                var counter = 0

                val genresList = it.genres.split(",").map { it.trim() }

                val numberOfGenres = if (genresList.size < 3) genresList.size else 3
                for(genre: String in genresList) {
                    if(counter == numberOfGenres) {
                        break
                    }
                    listOfChips[counter].text = genre
                    listOfChips[counter].visibility = View.VISIBLE
                    counter += 1
                }

                binding.tvGameDetailsPlatforms.text = it.platforms

                binding.tvGameDetailsReleaseDate.text = if(!it.released.isNullOrEmpty()) it.released else "-"
                binding.tvGameDetailsEsrbRating.text = if(!it.esrb_rating.isNullOrEmpty()) it.esrb_rating else "-"
                binding.tvGameDetailsDevelopers.text = if(!it.developers.isNullOrEmpty()) it.developers else "-"
                binding.tvGameDetailsPublishers.text = if(!it.publishers.isNullOrEmpty()) it.publishers else "-"

                if(!it.website.isNullOrEmpty()) {
                    binding.tvGameDetailsWebsite.text = it.website
                    binding.tvGameDetailsWebsite.setOnClickListener { v ->
                        try {
                            val uri = Uri.parse(it.website)
                            val intent = Intent(Intent.ACTION_VIEW, uri)
                            context?.startActivity(intent)
                        }
                        catch (e: Exception) { e.printStackTrace() }
                    }
                }
                else {
                    binding.tvGameDetailsWebsite.text = "-"
                }

                if(!it.reddit_url.isNullOrEmpty()) {
                    binding.tvGameDetailsReddit.text = it.reddit_url
                    binding.tvGameDetailsReddit.setOnClickListener { v ->
                        try {
                            val uri = Uri.parse(it.reddit_url)
                            val intent = Intent(Intent.ACTION_VIEW, uri)
                            context?.startActivity(intent)
                        }
                        catch (e: Exception) { e.printStackTrace() }
                    }
                }
                else {
                    binding.tvGameDetailsReddit.text = "-"
                }

            }
        }

        binding.ivGameDetailsBack.setOnClickListener {
            backToDiscoverGames()
        }

        gameDetailsViewModel.checkDoesGameExistAndSaveToDatabase()

        setUpScreenshotsRecyclerView()

        gameDetailsViewModel.screenshots.observe(viewLifecycleOwner) {
            if(!it.isNullOrEmpty()) {
                gameDetailsScreenshotAdapter.setData(it)
            }
        }

        binding.ivGameDetailsHot.setOnClickListener {
            showBadgeExplanationDialog(resources.getString(R.string.badge_explanation_title_hot_game), resources.getString(R.string.badge_explanation_message_hot_game))
        }

        binding.ivGameDetailsChallenging.setOnClickListener {
            showBadgeExplanationDialog(resources.getString(R.string.badge_explanation_title_challenging_game), resources.getString(R.string.badge_explanation_message_challenging_game))
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //full screen dialog fragment but with visible status bar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(
            STYLE_NORMAL,
            android.R.style.Theme_Black_NoTitleBar
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialog?.window?.attributes?.windowAnimations = R.style.MyDialogAnimation
    }

    private fun backToDiscoverGames() {
        findNavController().navigateUp()
    }

    private fun setUpScreenshotsRecyclerView() {
        gameDetailsScreenshotAdapter = GameDetailsScreenshotAdapter()
        binding.rvGameDetailsScreenshots.adapter = gameDetailsScreenshotAdapter
        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.rvGameDetailsScreenshots.layoutManager = layoutManager
    }

    private fun showBadgeExplanationDialog(title: String, message: String) {

        val builder = AlertDialog.Builder(requireActivity())
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.dialog_badge_explanation, null)
        builder.setView(dialogLayout)
        val dialog = builder.show()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val tvDeleteChannelDialogTitle = dialogLayout.findViewById<TextView>(R.id.tvBadgeExplanationDialogTitle)
        tvDeleteChannelDialogTitle.text = title
        val tvDeleteChannelDialogMessage = dialogLayout.findViewById<TextView>(R.id.tvBadgeExplanationDialogMessage)
        tvDeleteChannelDialogMessage.text = message
        val tvDismiss = dialogLayout.findViewById<TextView>(R.id.tvBadgeExplanationDialogDismiss)
        tvDismiss.setOnClickListener {
            dialog.dismiss()
        }
    }
}