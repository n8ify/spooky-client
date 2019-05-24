package com.n8ify.spooky.presentation._base

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.n8ify.spooky.R
import org.koin.android.viewmodel.ext.android.viewModel
import java.net.SocketTimeoutException

abstract class AbstractBaseActivity : AppCompatActivity() {

    private val progressDialog: ProgressDialog by lazy {
        ProgressDialog(this@AbstractBaseActivity).apply {
            setMessage(getString(R.string.common_loading))
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
    fun initUncaughtExceptionHandler() {}

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
            .setNeutralButton(R.string.common_close) { dialog, _ -> dialog.dismiss() }
            .create()
        alertDialog.show()
    }

    fun showAlertDialog(
        message: String,
        posButtonTitle: Int? = null,
        negButtonTitle: Int? = null,
        posDialog: DialogInterface.OnClickListener? = null,
        negDialog: DialogInterface.OnClickListener? = null
    ) {
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this@AbstractBaseActivity)
            .setMessage(message)

        if (posButtonTitle != null && posDialog != null) {
            alertDialog.setPositiveButton(posButtonTitle, posDialog)
        }

        if (negButtonTitle != null && negDialog != null) {
            alertDialog.setPositiveButton(negButtonTitle, negDialog)
        }

        alertDialog.create().show()
    }

}