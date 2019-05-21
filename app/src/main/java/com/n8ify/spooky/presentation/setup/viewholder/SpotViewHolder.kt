package com.n8ify.spooky.presentation.setup.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.n8ify.spooky.R
import com.n8ify.spooky.constant.*
import com.n8ify.spooky.model.spot.Spot
import com.n8ify.spooky.presentation._base.AbstractBaseViewHolder
import kotlinx.android.synthetic.main.view_spot.view.*

class SpotViewHolder(itemView: View) : AbstractBaseViewHolder(itemView) {
    fun bind(spot: Spot) {
        itemView.tv_spot_tale.text = spot.tale
        itemView.tv_spot_description.text = "${spot.description} ${spot.remark ?: "(${spot.remark})"}"
        itemView.tv_spot_latlng.text = "${spot.latitude}, ${spot.longitude}"
        itemView.tv_spot_status.text = when (spot.status) {
            SPOT_STATUS_ACTIVE -> getString(R.string.status_active)
            SPOT_STATUS_UNACTIVE -> getString(R.string.status_unactive)
            SPOT_STATUS_VISITED -> getString(R.string.status_visited)
            else -> getString(R.string.status_undefined)
        }
        itemView.tv_spot_timestamp.text = spot.createTimestamp.toString()
    }
}