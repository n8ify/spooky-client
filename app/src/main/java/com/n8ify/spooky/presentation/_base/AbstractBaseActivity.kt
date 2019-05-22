package com.n8ify.spooky.presentation._base

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class AbstractBaseActivity : AppCompatActivity() {


    private val progressDialog : ProgressDialog by lazy { ProgressDialog(this@AbstractBaseActivity).apply {
        setMessage("Loading...")
        setCancelable(false)
    }}

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        initPostCreateView()
        initPostCreateListener()
        initPostCreateObserver()
    }

    open fun initPostCreateView() {}
    open fun initPostCreateListener() {}
    open fun initPostCreateObserver() {

    }

    fun startActivity(activityClass: Class<out AppCompatActivity>) {
        startActivity(Intent(this, activityClass))
    }

    fun showLoadingDialog(){
        progressDialog.show()
    }

    fun hideLoadingDialog(){
        progressDialog.dismiss()
    }

    fun showAlertDialog(message : String){
        val alertDialog : AlertDialog = AlertDialog.Builder(this@AbstractBaseActivity)
            .setMessage(message)
            .create()
        alertDialog.show()
    }
}