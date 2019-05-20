package com.n8ify.spooky.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.n8ify.spooky.R
import com.n8ify.spooky.data.api.SpotAPI
import org.koin.android.viewmodel.ext.android.viewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private val mainViewMode : MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainViewMode.loadSpots()
    }
}
