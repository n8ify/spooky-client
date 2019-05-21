package com.n8ify.spooky.presentation.setup.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.n8ify.spooky.R
import com.n8ify.spooky.model.spot.Spot
import com.n8ify.spooky.presentation.setup.SetupActivity

class SaveSpotFragment : Fragment() {

    var spot: Spot? = null
    val setupViewModel by lazy { (activity as SetupActivity).spotViewModel }

    companion object {
        fun getInstance(spot: Spot?): SaveSpotFragment {
            return SaveSpotFragment().apply {
                this@apply.spot = spot
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(context).inflate(R.layout.fragment_save_spot, container, false)
    }
}