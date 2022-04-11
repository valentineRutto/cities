package com.valentinerutto.cities.di

import androidx.room.Room
import com.valentinerutto.cities.CitiesViewModel
import com.valentinerutto.cities.data.CitiesRepository
import com.valentinerutto.cities.local.CitiesOfTheWorldDatabase
import com.valentinerutto.cities.network.api.RetrofitClient.provideApiService
import com.valentinerutto.cities.network.api.RetrofitClient.provideOkHttpClient
import com.valentinerutto.cities.network.api.RetrofitClient.provideRetrofit
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private const val DB_NAME = "cities_of_the_world"

val appModule = module {
    single { provideRetrofit(okHttpClient = get()) }
    single { provideOkHttpClient() }
    single { provideApiService(retrofit = get()) }

    single {
        Room.databaseBuilder(
            androidContext(),
            CitiesOfTheWorldDatabase::class.java,
            DB_NAME
        ).build()
    }
    single { get<CitiesOfTheWorldDatabase>().citiesDao }


    single { CitiesRepository(apiService = get(), citiesDao = get()) }
    viewModel { CitiesViewModel(citiesRepository = get()) }
}
