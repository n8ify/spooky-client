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
    }

    override fun initPostCreateObserver() {
        mainViewModel.exception.observe(this@MainActivity, Observer {
            when (it) {
                is SocketTimeoutException -> {
                    Toast.makeText(this@MainActivity, getString(R.string.ghost_retry_connect_sentence), Toast.LENGTH_LONG).show()
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
            Toast.makeText(this@MainActivity, getString(R.string.ghost_ready_sentence), Toast.LENGTH_LONG).show()
        })
        mainViewModel.latLng.observe(this@MainActivity, Observer {
            mainViewModel.getNearestSpot()?.let {
                if(it.spot != null){
                    tv_tale.text = it.spot.tale
                    tv_description.text = it.spot.description
                    tv_distance.text = "${it.distance} ${getString(R.string.common_meter)}"
                }
            }
        })
    }

    override fun initPostLoadAsync() {
        mainViewModel.loadSpots()
    }
}
