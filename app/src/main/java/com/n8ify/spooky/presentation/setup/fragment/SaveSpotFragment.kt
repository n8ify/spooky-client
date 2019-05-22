package com.n8ify.spooky.presentation.setup.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.jakewharton.rxbinding2.view.RxView
import com.n8ify.spooky.R
import com.n8ify.spooky.constant.SPOT_STATUS_ACTIVE
import com.n8ify.spooky.model.spot.Spot
import com.n8ify.spooky.presentation._base.AbstractBaseFragment
import com.n8ify.spooky.presentation.setup.SetupActivity
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.fragment_save_spot.*

class SaveSpotFragment : AbstractBaseFragment() {

    var spot: Spot? = null
    private val setupViewModel by lazy { (activity as SetupActivity).spotViewModel }

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

    override fun initPostViewCreatedListener() {
        RxView.clicks(btn_pin).subscribeBy {
            if (edtx_tale.text.isEmpty()) {
                Toast.makeText(context, getString(R.string.warning_message_tale_required), Toast.LENGTH_LONG).show()
            } else {
                if (spot == null) {
                    setupViewModel.latLng.value?.let { latLng ->
                        setupViewModel.insertSpot(
                            Spot(
                                -1
                                , edtx_tale.text.toString()
                                , edtx_description.text.toString()
                                , edtx_remark.text.toString()
                                , SPOT_STATUS_ACTIVE
                                , latLng.latitude
                                , latLng.longitude
                            )
                        )
                    }
                } else {
                    setupViewModel.latLng.value?.let { latLng ->
                        setupViewModel.updateSpot(
                            Spot(
                                spot!!.id
                                , edtx_tale.text.toString()
                                , edtx_description.text.toString()
                                , edtx_remark.text.toString()
                                , SPOT_STATUS_ACTIVE
                                , latLng.latitude
                                , latLng.longitude
                            )
                        )
                    }
                }
                setupViewModel.spots.observe(this, Observer {
                    edtx_tale.text.clear()
                    edtx_description.text.clear()
                    edtx_remark.text.clear()
                })
            }
        }
    }

    override fun initPostViewCreatedObserver() {
        setupViewModel.latLng.observe(this, Observer {
            tv_latlng.text = "${it.latitude}, ${it.longitude}"
        })
    }

    fun editSpot(spot: Spot) {
        this@SaveSpotFragment.spot = spot
        edtx_tale.setText(spot.tale)
        edtx_description.setText(spot.description)
        edtx_remark.setText(spot.remark)
    }
}