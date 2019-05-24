package com.n8ify.spooky.presentation.main

import android.content.DialogInterface
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.jakewharton.rxbinding2.view.RxView
import com.n8ify.spooky.R
import com.n8ify.spooky.presentation._base.AbstractBaseActivity
import com.n8ify.spooky.presentation.setup.SetupActivity
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
            // TODO : If Spot Information is Changed.
            if(it.isEmpty()){
                tv_tale.text = "-"
                tv_description.text = "-"
                tv_distance.text = "-"
            } else {
                locateNearestSpot()
                rb_distance_beat.startRippleAnimation()
            }
        })
        mainViewModel.latLng.observe(this@MainActivity, Observer {
            locateNearestSpot()
        })
    }

    override fun initPostLoadAsync() {
        mainViewModel.loadSpots()
    }

    fun locateNearestSpot(){
        mainViewModel.getNearestSpot()?.let {
            if (it.spot != null) {
                tv_tale.text = it.spot.tale
                tv_description.text = it.spot.description
                tv_distance.text = "${it.distance} ${getString(R.string.common_meter)}"
            }
        }
    }
}
