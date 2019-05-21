package com.n8ify.spooky.presentation._base

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseAbstractActivity : AppCompatActivity() {


    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        initPostCreateView()
        initPostCreateListener()
        initPostCreateObserver()
    }

    open fun initPostCreateView() {}
    open fun initPostCreateListener() {}
    open fun initPostCreateObserver() {}

    fun startActivity(activityClass: Class<out AppCompatActivity>) {
        startActivity(Intent(this, activityClass))
    }

}