package com.n8ify.spooky

import android.app.Application
import com.n8ify.spooky.module.dataModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class SpookyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val koin = startKoin {
            androidContext(this@SpookyApplication)
            modules(dataModule)
        }
    }
}