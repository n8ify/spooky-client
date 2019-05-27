package com.n8ify.spooky.presentation.main

import android.content.DialogInterface
import android.os.Bundle
import android.os.PersistableBundle
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.jakewharton.rxbinding2.view.RxView
import com.n8ify.spooky.R
import com.n8ify.spooky.constant.*
import com.n8ify.spooky.presentation._base.AbstractBaseActivity
import com.n8ify.spooky.presentation.setup.SetupActivity
import com.skyfishjy.library.RippleBackground
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.net.SocketTimeoutException

class MainActivity : AbstractBaseActivity() {

    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun initPostCreateView() {}

    override fun initPostCreateListener() {
        RxView.clicks(tv_setting).subscribeBy { startActivity(SetupActivity::class.java) }
        RxView.longClicks(btn_visited).subscribeBy {
            val nearestSpot = mainViewModel.getNearestSpot()
            nearestSpot?.spot?.let {
                mainViewModel.deleteSpot(it)
            }
        }
    }

    override fun initPostCreateObserver() {
        mainViewModel.exception.observe(this@MainActivity, Observer {
            when (it) {
                is SocketTimeoutException -> {
                    showAlertDialog(
                        getString(R.string.ghost_retry_connect_sentence),
                        posButtonTitle = R.string.common_retry,
                        posDialog = DialogInterface.OnClickListener { dialog, which ->
                            mainViewModel.loadSpots() // Action : Retry
                        })
                }
            }
        })
        mainViewModel.isLoading.observe(this@MainActivity, Observer { isLoading ->
            if (isLoading) {
                showLoadingDialog()
            } else {
                hideLoadingDialog()
            }
        })
        mainViewModel.spots.observe(this@MainActivity, Observer {
            if (it.isEmpty()) {
                tv_tale.text = "-"
                tv_description.text = "-"
                tv_distance.text = "-"
                btn_visited.isEnabled = false
            } else {
                locateNearestSpot()
                btn_visited.isEnabled = true
            }
        })
        mainViewModel.latLng.observe(this@MainActivity, Observer {
            locateNearestSpot()
        })
    }

    override fun initPostLoadAsync() {
        mainViewModel.loadSpots()
    }

    fun locateNearestSpot() {
        mainViewModel.getNearestSpot()?.let {
            if (it.spot != null) {
                tv_tale.text = it.spot.tale
                tv_description.text = it.spot.description
                val distanceMessage = when {
                    it.distance > CLOSE_METER_DISTANCE_LEVEL_5 -> {
                        activateDistanceRippleBeat(CLOSE_METER_DISTANCE_LEVEL_5)
                        getString(R.string.ghost_response_no_spot)
                    }
                    it.distance > CLOSE_METER_DISTANCE_LEVEL_4 -> {
                        activateDistanceRippleBeat(CLOSE_METER_DISTANCE_LEVEL_4)
                        getString(R.string.ghost_response_become_closer)
                    }
                    it.distance > CLOSE_METER_DISTANCE_LEVEL_3 -> {
                        activateDistanceRippleBeat(CLOSE_METER_DISTANCE_LEVEL_3)
                        getString(R.string.ghost_response_just_close_to_it)
                    }
                    it.distance > CLOSE_METER_DISTANCE_LEVEL_2 -> {
                        activateDistanceRippleBeat(CLOSE_METER_DISTANCE_LEVEL_2)
                        getString(R.string.ghost_response_must_be_here)
                    }
                    it.distance > CLOSE_METER_DISTANCE_LEVEL_1 || it.distance < CLOSE_METER_DISTANCE_LEVEL_1 -> {
                        activateDistanceRippleBeat(CLOSE_METER_DISTANCE_LEVEL_1)
                        getString(R.string.ghost_response_exactly_around_here)
                    }
                    else -> {
                        activateDistanceRippleBeat(CLOSE_METER_DISTANCE_LEVEL_5)
                        getString(R.string.ghost_response_no_spot)
                    }
                }
                tv_distance.text = "$distanceMessage : ${it.distance} ${getString(R.string.common_meter)}"

            }
        }
    }

    fun activateDistanceRippleBeat(level: Int) {
        when (level) {
            CLOSE_METER_DISTANCE_LEVEL_1 -> {
                rb_distance_beat_lv1.startRippleAnimation()
                rb_distance_beat_lv1.visibility = View.VISIBLE
                rb_distance_beat_lv2.stopRippleAnimation()
                rb_distance_beat_lv2.visibility = View.GONE
                rb_distance_beat_lv3.stopRippleAnimation()
                rb_distance_beat_lv3.visibility = View.GONE
                rb_distance_beat_lv4.stopRippleAnimation()
                rb_distance_beat_lv4.visibility = View.GONE
                rb_distance_beat_lv5.stopRippleAnimation()
                rb_distance_beat_lv5.visibility = View.GONE
            }
            CLOSE_METER_DISTANCE_LEVEL_2 -> {
                rb_distance_beat_lv1.stopRippleAnimation()
                rb_distance_beat_lv1.visibility = View.GONE
                rb_distance_beat_lv2.startRippleAnimation()
                rb_distance_beat_lv2.visibility = View.VISIBLE
                rb_distance_beat_lv3.stopRippleAnimation()
                rb_distance_beat_lv3.visibility = View.GONE
                rb_distance_beat_lv4.stopRippleAnimation()
                rb_distance_beat_lv4.visibility = View.GONE
                rb_distance_beat_lv5.stopRippleAnimation()
                rb_distance_beat_lv5.visibility = View.GONE
            }
            CLOSE_METER_DISTANCE_LEVEL_3 -> {
                rb_distance_beat_lv1.stopRippleAnimation()
                rb_distance_beat_lv1.visibility = View.GONE
                rb_distance_beat_lv2.stopRippleAnimation()
                rb_distance_beat_lv2.visibility = View.GONE
                rb_distance_beat_lv3.startRippleAnimation()
                rb_distance_beat_lv3.visibility = View.VISIBLE
                rb_distance_beat_lv4.stopRippleAnimation()
                rb_distance_beat_lv4.visibility = View.GONE
                rb_distance_beat_lv5.stopRippleAnimation()
                rb_distance_beat_lv5.visibility = View.GONE
            }
            CLOSE_METER_DISTANCE_LEVEL_4 -> {
                rb_distance_beat_lv1.stopRippleAnimation()
                rb_distance_beat_lv1.visibility = View.GONE
                rb_distance_beat_lv2.stopRippleAnimation()
                rb_distance_beat_lv2.visibility = View.GONE
                rb_distance_beat_lv3.stopRippleAnimation()
                rb_distance_beat_lv3.visibility = View.GONE
                rb_distance_beat_lv4.startRippleAnimation()
                rb_distance_beat_lv4.visibility = View.VISIBLE
                rb_distance_beat_lv5.stopRippleAnimation()
                rb_distance_beat_lv5.visibility = View.GONE
            }
            CLOSE_METER_DISTANCE_LEVEL_5 -> {
                rb_distance_beat_lv1.stopRippleAnimation()
                rb_distance_beat_lv1.visibility = View.GONE
                rb_distance_beat_lv2.stopRippleAnimation()
                rb_distance_beat_lv2.visibility = View.GONE
                rb_distance_beat_lv3.stopRippleAnimation()
                rb_distance_beat_lv3.visibility = View.GONE
                rb_distance_beat_lv4.stopRippleAnimation()
                rb_distance_beat_lv4.visibility = View.GONE
                rb_distance_beat_lv5.startRippleAnimation()
                rb_distance_beat_lv5.visibility = View.VISIBLE
            }
        }
    }
}
