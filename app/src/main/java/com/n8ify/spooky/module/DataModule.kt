package com.n8ify.spooky.module

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.n8ify.spooky.data.api.SpotAPI
import com.n8ify.spooky.data.repository.SpotRepository
import com.n8ify.spooky.data.repository.SpotRepositoryImpl
import com.n8ify.spooky.presentation.main.MainViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {
    single { provideRetrofit().create(SpotAPI::class.java) }
    factory<SpotRepository> { SpotRepositoryImpl(spotAPI = get()) }
    viewModel { MainViewModel(spotRepository = get()) }
}

fun provideOkHttp() : OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
        .build()
}

fun provideRetrofit() : Retrofit {
    return Retrofit.Builder()
        .client(provideOkHttp())
        .baseUrl("https://radiant-shore-70316.herokuapp.com/")
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}