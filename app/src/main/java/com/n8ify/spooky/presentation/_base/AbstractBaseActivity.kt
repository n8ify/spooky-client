package com.n8ify.spooky.presentation._base

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import org.koin.android.viewmodel.ext.android.viewModel
import java.net.SocketTimeoutException

abstract class AbstractBaseActivity : AppCompatActivity() {

    private val progressDialog: ProgressDialog by lazy {
        ProgressDialog(this@AbstractBaseActivity).apply {
            setMessage("Loading...")
            setCancelable(false)
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        initPostCreateView()
        initPostCreateListener()
        initPostCreateObserver()
        initPostLoadAsync()
    }

    open fun initPostCreateView() {}
    open fun initPostCreateListener() {}
    open fun initPostCreateObserver() {}
    open fun initPostLoadAsync() {}

    fun startActivity(activityClass: Class<out AppCompatActivity>) {
        startActivity(Intent(this, activityClass))
    }

    fun showLoadingDialog() {
        progressDialog.show()
    }

    fun hideLoadingDialog() {
        progressDialog.dismiss()
    }

    fun showAlertDialog(message: String) {
        val alertDialog: AlertDialog = AlertDialog.Builder(this@AbstractBaseActivity)
            .setMessage(message)
            .create()
        alertDialog.show()
    }
}