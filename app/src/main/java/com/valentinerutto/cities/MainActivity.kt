package com.valentinerutto.cities

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.recyclerview.widget.RecyclerView
import com.valentinerutto.cities.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val citiesViewModel by viewModel<CitiesViewModel>()
    private var currentPage = 1
    private lateinit var citiesAdapter: CitiesAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        citiesAdapter = CitiesAdapter()

        citiesViewModel.init(page = currentPage, isFreshStart = true)
        setupObservables()

    }

    private fun setupObservables() {
        binding.mapLayout.isVisible = false
        binding.resultLayout.isVisible = true
        citiesViewModel.isLoading.observe(this) { showLoading ->
            binding.citiesProgressBar.isVisible = showLoading
        }

        citiesViewModel.errorCitiesResponse.observe(this) { errorMsg ->
            binding.citiesErrorTextView.text = errorMsg
        }

        citiesViewModel.successfulCitiesResponse.observe(this) { citiesResponse ->
            binding.citiesRecyclerView.adapter = citiesAdapter.apply {
                submitList(citiesResponse)
                citiesViewModel.currentCityList.value = citiesResponse

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

    private fun showMap() {

        val bundle = bundleOf("page" to currentPage)
        binding.mapLayout.isVisible = true
        binding.resultLayout.isVisible = false
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<MapsFragment>(R.id.mapLayout, args = bundle)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.view_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.list -> {
                setupObservables()
                true
            }
            R.id.map -> {
                showMap()


                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}