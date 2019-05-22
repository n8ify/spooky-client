package com.n8ify.spooky.presentation._base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

abstract class AbstractBaseViewModel(application: Application) : AndroidViewModel(application) {
    var TAG = AbstractBaseViewModel::class.java.simpleName
    val exception = MutableLiveData<Throwable>()
    val isLoading = MutableLiveData<Boolean>()

}