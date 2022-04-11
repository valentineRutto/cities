package com.valentinerutto.citiesoftheworld

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valentinerutto.cities.core.Resource
import com.valentinerutto.cities.data.CitiesRepository
import com.valentinerutto.cities.local.CityEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CitiesViewModel(private val citiesRepository: CitiesRepository) : ViewModel() {

    private val _successfulCitiesResponse = MutableLiveData<List<CityEntity>?>()
    val successfulCitiesResponse: LiveData<List<CityEntity>?>
        get() = _successfulCitiesResponse

    private val _errorCitiesResponse = MutableLiveData<String>()
    val errorCitiesResponse: LiveData<String>
        get() = _errorCitiesResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun init(page: Int, isFreshStart: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            if (isFreshStart)
                _isLoading.postValue(true)
            fetchCities(page = page)
        }
    }

    fun getTotalPages() = citiesRepository.getTotalPages()

    suspend fun fetchCities(page: Int) {
        when (val citiesResponse = citiesRepository.getCities(page = page)) {
            is Resource.Success -> {
                _isLoading.postValue(false)
                _successfulCitiesResponse.postValue(citiesResponse.data)
            }
            is Resource.Error -> {
                _isLoading.postValue(false)
                _errorCitiesResponse.postValue(citiesResponse.errorMessage)
            }
        }
    }
}
