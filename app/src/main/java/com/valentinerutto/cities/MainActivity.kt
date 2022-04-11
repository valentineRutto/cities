package com.valentinerutto.cities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
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

        setupObservables()
        citiesViewModel.init(page = currentPage, isFreshStart = true)

    }

    private fun setupObservables() {
        citiesViewModel.isLoading.observe(this) { showLoading ->
            binding.citiesProgressBar.isVisible = showLoading
        }

        citiesViewModel.errorCitiesResponse.observe(this) { errorMsg ->
            binding.citiesErrorTextView.text = errorMsg
        }

        citiesViewModel.successfulCitiesResponse.observe(this) { citiesResponse ->
            binding.citiesRecyclerView.adapter = CitiesAdapter().apply {
                submitList(citiesResponse)
            }
            binding.citiesRecyclerView.addOnScrollListener(
                object : RecyclerView.OnScrollListener() {

                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        if (dy > 0) {
                            val nextPage = currentPage + 1
                            if (nextPage < citiesViewModel.getTotalPages()) {
                                citiesViewModel.init(page = nextPage, isFreshStart = false)
                            }
                        }
                    }
                }
            )
        }
    }


}