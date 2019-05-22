package com.n8ify.spooky.presentation.setup.viewholder

import android.app.AlertDialog
import android.view.View
import android.widget.ArrayAdapter
import com.jakewharton.rxbinding2.view.RxView
import com.n8ify.spooky.R
import com.n8ify.spooky.constant.SPOT_STATUS_ACTIVE
import com.n8ify.spooky.constant.SPOT_STATUS_UNACTIVE
import com.n8ify.spooky.constant.SPOT_STATUS_VISITED
import com.n8ify.spooky.model.spot.Spot
import com.n8ify.spooky.presentation._base.AbstractBaseViewHolder
import com.n8ify.spooky.presentation._event.OnSelectSpotOption
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.view_spot.view.*

class SpotViewHolder(itemView: View) : AbstractBaseViewHolder(itemView) {

    val OPTION_VIEW_ON_GOOGLE_MAP = 0
    val OPTION_EDIT = 1
    val OPTION_DELETE = 2

    fun bind(spot: Spot) {
        itemView.tv_spot_tale.text = spot.tale
        itemView.tv_spot_description.text = String.format("%s %s", "${spot.description} ", if (spot.remark != null) { " (${spot.remark})" } else { "" })
        itemView.tv_spot_latlng.text = "${spot.latitude}, ${spot.longitude}"
        itemView.tv_spot_status.text = when (spot.status) {
            SPOT_STATUS_ACTIVE -> getString(R.string.status_active)
            SPOT_STATUS_UNACTIVE -> getString(R.string.status_unactive)
            SPOT_STATUS_VISITED -> getString(R.string.status_visited)
            else -> getString(R.string.status_undefined)
        }
        itemView.tv_spot_timestamp.text = spot.createTimestamp.toString()


        val optionAdapter = ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, getContext().resources.getStringArray(R.array.spot_option))
        RxView.clicks(itemView).subscribeBy {
            AlertDialog.Builder(getContext())
                .setAdapter(optionAdapter) { _, which ->
                   with(getContext() as OnSelectSpotOption){
                       when (which) {
                           OPTION_VIEW_ON_GOOGLE_MAP -> {
                                this.onViewOnGoogleMapClick(spot)
                           }
                           OPTION_EDIT -> {
                                this.onEditClick(spot)
                           }
                           OPTION_DELETE -> {
                                this.onDeleteClick(spot)
                           }
                       }
                   }
                }.create().show()
        }
    }
}