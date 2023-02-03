package com.msardevelopment.rawggameapp.ui.discovergames

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.msardevelopment.rawggameapp.R
import com.msardevelopment.rawggameapp.data.model.apimodel.gamesbygenresresponse.Genre
import com.msardevelopment.rawggameapp.data.model.apimodel.gamesbygenresresponse.Result

class GamesPagedAdapter(
    val openGameDetails: (Int) -> Unit,
    val showBadgeExplanationDialog: (String, String) -> Unit
): PagingDataAdapter<Result, GamesPagedAdapter.GameItemViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Result>() {
            override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean =
                oldItem == newItem
        }
    }

    private lateinit var context: Context

    inner class GameItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivGameImage: ImageView = itemView.findViewById(R.id.ivDiscoverGamesGameImage)
        val ivHot: ImageView = itemView.findViewById(R.id.ivDiscoverGamesGameHot)
        val ivChallenging: ImageView = itemView.findViewById(R.id.ivDiscoverGamesGameChallenging)
        val tvGameMetacriticscore: TextView = itemView.findViewById(R.id.tvDiscoverGamesGameMetacritic)
        val tvGameTitle: TextView = itemView.findViewById(R.id.tvDiscoverGamesGameTitle)
        val tvGenreChip1: TextView = itemView.findViewById(R.id.tvDiscoverGamesGameGenre1)
        val tvGenreChip2: TextView = itemView.findViewById(R.id.tvDiscoverGamesGameGenre2)
        val tvGenreChip3: TextView = itemView.findViewById(R.id.tvDiscoverGamesGameGenre3)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameItemViewHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_game, parent, false)
        return GameItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: GameItemViewHolder, position: Int) {
        val currentItem = getItem(position)

        holder.apply {
            ivGameImage.load(currentItem!!.backgroundImage)

            val owned = currentItem.addedByStatus.owned
            val playing = currentItem.addedByStatus.playing
            val beaten = currentItem.addedByStatus.beaten
            val playingRatio = playing.toDouble() / owned
            val beatenRatio = beaten.toDouble() / owned

            ivHot.visibility = View.GONE
            ivChallenging.visibility = View.GONE
            ivHot.visibility = if (playingRatio > 0.04) View.VISIBLE else View.GONE
            ivChallenging.visibility = if (beatenRatio < 0.03) View.VISIBLE else View.GONE

            ivHot.setOnClickListener {
                showBadgeExplanationDialog(context.resources.getString(R.string.badge_explanation_title_hot_game), context.resources.getString(R.string.badge_explanation_message_hot_game))
            }

            ivChallenging.setOnClickListener {
                showBadgeExplanationDialog(context.resources.getString(R.string.badge_explanation_title_challenging_game), context.resources.getString(R.string.badge_explanation_message_challenging_game))
            }

            when (currentItem.metacritic) {
                in 75..100 -> {
                    tvGameMetacriticscore.setBackgroundResource(R.drawable.bg_rounded_score_high)
                    tvGameMetacriticscore.setTextColor(context.resources.getColor(R.color.black))
                    tvGameMetacriticscore.text = currentItem!!.metacritic.toString()
                }
                in 50..74 -> {
                    tvGameMetacriticscore.setBackgroundResource(R.drawable.bg_rounded_score_mid)
                    tvGameMetacriticscore.setTextColor(context.resources.getColor(R.color.black))
                    tvGameMetacriticscore.text = currentItem!!.metacritic.toString()
                }
                in 0..49 -> {
                    tvGameMetacriticscore.setBackgroundResource(R.drawable.bg_rounded_score_low)
                    tvGameMetacriticscore.setTextColor(context.resources.getColor(R.color.white))
                    tvGameMetacriticscore.text = currentItem!!.metacritic.toString()
                }
                else -> {
                    tvGameMetacriticscore.setBackgroundResource(R.drawable.bg_rounded_score_no_score)
                    tvGameMetacriticscore.setTextColor(context.resources.getColor(R.color.white))
                    tvGameMetacriticscore.text = context.resources.getString(R.string.game_details_metacritic_no_score)
                }
            }

            tvGameTitle.text = currentItem!!.name

            tvGenreChip1.visibility = View.GONE
            tvGenreChip2.visibility = View.GONE
            tvGenreChip3.visibility = View.GONE
            val listOfChips = listOf(tvGenreChip1, tvGenreChip2, tvGenreChip3)
            var counter = 0
            val numberOfGenres = if (currentItem!!.genres.size < 3) currentItem!!.genres.size else 3
            for(genre: Genre in currentItem!!.genres) {
                if(counter == numberOfGenres) {
                    break
                }
                listOfChips[counter].text = genre.name
                listOfChips[counter].visibility = View.VISIBLE
                counter += 1
            }

            itemView.setOnClickListener {
                openGameDetails(currentItem.id)
            }
        }
    }
}