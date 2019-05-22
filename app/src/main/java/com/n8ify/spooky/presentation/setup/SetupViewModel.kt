package com.n8ify.spooky.presentation.setup

import android.app.Application
import com.n8ify.spooky.data.repository.SpotRepository
import com.n8ify.spooky.presentation._base.AbstractSpotViewModel

/**
 * Tag : AbstractLocationViewModel */
class SetupViewModel(spotRepository: SpotRepository, application: Application) : AbstractSpotViewModel(spotRepository, application) {

    init{
        TAG = SetupViewModel::class.java.simpleName
    }

}