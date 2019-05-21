package com.n8ify.spooky.presentation.main

import android.os.Bundle
import com.jakewharton.rxbinding2.view.RxView
import com.n8ify.spooky.R
import com.n8ify.spooky.presentation._base.AbstractBaseActivity
import com.n8ify.spooky.presentation.setup.SetupActivity
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AbstractBaseActivity() {

    private val mainViewMode : MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun initPostCreateView(){}

    override fun initPostCreateListener() {
        RxView.clicks(tv_setting).subscribeBy { startActivity(SetupActivity::class.java) }
    }

}
