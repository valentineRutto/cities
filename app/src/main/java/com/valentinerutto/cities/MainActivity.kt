package com.valentinerutto.cities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.valentinerutto.cities.databinding.ActivityMainBinding
import com.valentinerutto.citiesoftheworld.CitiesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val citiesViewModel by viewModel<CitiesViewModel>()
    private var currentPage = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}