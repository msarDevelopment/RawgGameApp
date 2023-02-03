package com.msardevelopment.rawggameapp.ui.genreselection

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.msardevelopment.rawggameapp.R
import com.msardevelopment.rawggameapp.data.model.databasemodel.GenreDb

class GenreSelectionAdapter(
    val openGenreDetailsBottomSheet: (GenreDb) -> Unit,
    val setGenreSelected: (Int, Boolean) -> Unit
): RecyclerView.Adapter<GenreSelectionAdapter.GenreSelectionViewHolder>() {

    var genres = emptyList<GenreDb>()

    inner class GenreSelectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cbGenreSelected: CheckBox = itemView.findViewById(R.id.cbGenreSelected)
        val ivOpenGenreDetails: ImageView = itemView.findViewById(R.id.ivOpenGenreDetails)
        val tvGenreItemLabel: TextView = itemView.findViewById(R.id.tvGenreItemLabel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreSelectionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_genre, parent, false)
        return GenreSelectionViewHolder(view)
    }

    override fun onBindViewHolder(holder: GenreSelectionViewHolder, position: Int) {

        val currentItem = genres[position]

        holder.apply {

            cbGenreSelected.setOnCheckedChangeListener (null);
            cbGenreSelected.isChecked = currentItem.is_selected
            cbGenreSelected.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    currentItem.external_id?.let { setGenreSelected(it, true) }
                } else {
                    currentItem.external_id?.let { setGenreSelected(it, false) }
                }
            }

            itemView.setOnClickListener {
                cbGenreSelected.isChecked = !cbGenreSelected.isChecked
            }

            tvGenreItemLabel.text = currentItem.name

            ivOpenGenreDetails.setOnClickListener {
                openGenreDetailsBottomSheet(currentItem)
            }
        }
    }

    override fun getItemCount(): Int {
        return genres.size
    }

    fun setData(genres: List<GenreDb>) {
        this.genres = genres
        notifyDataSetChanged()
    }
}