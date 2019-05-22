package com.n8ify.spooky.presentation._event

import com.n8ify.spooky.model.spot.Spot

interface OnSelectSpotOption {
    fun onViewOnGoogleMapClick(spot : Spot)
    fun onEditClick(spot : Spot)
    fun onDeleteClick(index: Spot)
}