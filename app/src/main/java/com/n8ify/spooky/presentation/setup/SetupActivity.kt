package com.n8ify.spooky.presentation.setup

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.n8ify.spooky.R
import com.n8ify.spooky.model.spot.Spot
import com.n8ify.spooky.presentation._base.AbstractBaseActivity
import com.n8ify.spooky.presentation._event.OnSelectSpotOption
import com.n8ify.spooky.presentation.setup.adapter.SetupPagerAdapter
import com.n8ify.spooky.presentation.setup.fragment.RegisteredSpotFragment
import com.n8ify.spooky.presentation.setup.fragment.SaveSpotFragment
import kotlinx.android.synthetic.main.activity_setup.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.net.SocketTimeoutException

class SetupActivity : AbstractBaseActivity(), OnSelectSpotOption {

    val spotViewModel: SetupViewModel by viewModel()

    val registeredSpotFragment by lazy { RegisteredSpotFragment.getInstance() }
    val saveSpotFragment by lazy { SaveSpotFragment.getInstance(null) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setup)
    }

    override fun initPostCreateObserver() {
        spotViewModel.exception.observe(this, Observer {
            when (it) {
                is SocketTimeoutException -> {
                    Toast.makeText(this@SetupActivity, it.localizedMessage, Toast.LENGTH_LONG).show()
                }
            }
        })
        spotViewModel.isLoading.observe(this, Observer { isLoading ->
            if (isLoading) {
                showLoadingDialog()
            } else {
                hideLoadingDialog()
            }
        })
        vp_setup.adapter = SetupPagerAdapter.getInstance(
            supportFragmentManager,
            registeredSpotFragment,
            saveSpotFragment
        )
    }

    override fun onViewOnGoogleMapClick(spot : Spot) {
        val geoUri = Uri.parse("geo:${spot.latitude},${spot.longitude}")
        val googleMapIntent = Intent(Intent.ACTION_VIEW, geoUri)
        googleMapIntent.setPackage("com.google.android.apps.maps")
        if(googleMapIntent.resolveActivity(packageManager) != null){
            startActivity(googleMapIntent)
        }
    }

    override fun onEditClick(spot : Spot) {
        vp_setup.currentItem = 1
        saveSpotFragment.editSpot(spot)
    }

    override fun onDeleteClick(spot: Spot) {
        spotViewModel.deleteSpot(spot)
    }

    override fun onBackPressed() {
        if(vp_setup.currentItem == 0){
            finish()
        } else {
            vp_setup.currentItem = 0
        }
    }
}
