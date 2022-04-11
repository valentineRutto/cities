package com.valentinerutto.cities

import android.app.Application
import com.valentinerutto.cities.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class CitiesOfTheWorldApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CitiesOfTheWorldApplication)
            modules(listOf(appModule))
        }
    }
}
