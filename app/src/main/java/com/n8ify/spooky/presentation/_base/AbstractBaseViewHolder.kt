package com.n8ify.spooky.presentation._base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class AbstractBaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun getContext() = itemView.context
    fun getString(resId: Int) = getContext().getString(resId)
}