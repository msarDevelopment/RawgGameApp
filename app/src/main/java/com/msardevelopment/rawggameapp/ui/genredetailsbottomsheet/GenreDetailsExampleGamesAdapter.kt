package com.msardevelopment.rawggameapp.ui.genredetailsbottomsheet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.msardevelopment.rawggameapp.R
import com.msardevelopment.rawggameapp.data.model.databasemodel.GameDb

class GenreDetailsExampleGamesAdapter() : RecyclerView.Adapter<GenreDetailsExampleGamesAdapter.GenreDetailsExampleGamesViewHolder>() {

    var games = emptyList<GameDb>()

    inner class GenreDetailsExampleGamesViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val ivGenreExampleGameImage: ImageView = itemView.findViewById(R.id.ivGenreExampleGameImage)
        val tvGenreExampleGameTitle: TextView = itemView.findViewById(R.id.tvGenreExampleGameTitle)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GenreDetailsExampleGamesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_genre_example_game, parent, false)
        return GenreDetailsExampleGamesViewHolder(view)
    }

    override fun onBindViewHolder(holder: GenreDetailsExampleGamesViewHolder, position: Int) {
        val currentItem = games[position]
        holder.apply {
            tvGenreExampleGameTitle.text = currentItem.name
            ivGenreExampleGameImage.load(currentItem.background_image)
        }
    }

    override fun getItemCount(): Int {
        return games.size
    }

    fun setData(games: List<GameDb>) {
        this.games = games
        notifyDataSetChanged()
    }
}