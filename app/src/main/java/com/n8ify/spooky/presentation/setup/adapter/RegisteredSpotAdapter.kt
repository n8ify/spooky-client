package com.n8ify.spooky.presentation.setup.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.n8ify.spooky.R
import com.n8ify.spooky.model.spot.Spot
import com.n8ify.spooky.presentation.setup.viewholder.SpotViewHolder

class RegisteredSpotAdapter : RecyclerView.Adapter<SpotViewHolder>() {

    lateinit var spots: List<Spot>

    companion object {
        fun getInstance(spots: List<Spot>): RegisteredSpotAdapter {
            return RegisteredSpotAdapter().apply {
                this@apply.spots = spots
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpotViewHolder {
        return SpotViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_spot, parent, false))
    }

    override fun getItemCount(): Int = spots.size

    override fun onBindViewHolder(holder: SpotViewHolder, position: Int) {
        holder.bind(spots[position])
    }
}