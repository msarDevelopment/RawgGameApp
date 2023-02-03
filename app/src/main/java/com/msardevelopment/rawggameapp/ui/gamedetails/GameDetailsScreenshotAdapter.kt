package com.msardevelopment.rawggameapp.ui.gamedetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.msardevelopment.rawggameapp.R
import com.msardevelopment.rawggameapp.data.model.databasemodel.ScreenshotDb

class GameDetailsScreenshotAdapter() : RecyclerView.Adapter<GameDetailsScreenshotAdapter.GameDetailsScreenshotViewHolder>() {

    var screenshots = emptyList<ScreenshotDb>()

    inner class GameDetailsScreenshotViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val ivGameDetailsScreenshotItem: ImageView = itemView.findViewById(R.id.ivGameDetailsScreenshotItem)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GameDetailsScreenshotViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_screenshot, parent, false)
        return GameDetailsScreenshotViewHolder(view)
    }

    override fun onBindViewHolder(holder: GameDetailsScreenshotViewHolder, position: Int) {
        val currentItem = screenshots[position]
        holder.apply {
            ivGameDetailsScreenshotItem.load(currentItem.link)
        }
    }

    override fun getItemCount(): Int {
        return screenshots.size
    }

    fun setData(screenshots: List<ScreenshotDb>) {
        this.screenshots = screenshots
        notifyDataSetChanged()
    }
}