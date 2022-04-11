package com.valentinerutto.cities.data

import com.valentinerutto.cities.core.Resource
import com.valentinerutto.cities.local.CitiesDao
import com.valentinerutto.cities.local.CityEntity
import com.valentinerutto.cities.network.api.ApiService
import com.valentinerutto.cities.network.model.CitiesResponse

class CitiesRepository(
    private val apiService: ApiService,
    private val citiesDao: CitiesDao
) {
    private var lastPage: Int = -1

    suspend fun getCities(page: Int, include: String = "country"): Resource<List<CityEntity>> {
        val cities = citiesDao.getCities(page)

        if (cities.isNotEmpty()) return Resource.Success(cities)

        val response = apiService.getCities(page = page, include = include)

        if (!response.isSuccessful) return Resource.Error<List<CityEntity>>(errorMessage = response.message())

        val citiesResponse = response.body()

        lastPage = citiesResponse?.data?.pagination?.lastPage ?: -1

        val cityEntities = mapResponseToEntity(citiesResponse)

        citiesDao.saveCities(cityEntities = cityEntities)

        return Resource.Success(data = cityEntities)
    }

    fun getTotalPages() = lastPage

    suspend fun fetchCities(name: String): Resource<List<CityEntity>> {
        val city = citiesDao.filterByCityName(name)
        return Resource.Success(data = city ?: emptyList())

    }

    private fun mapResponseToEntity(citiesResponse: CitiesResponse?): List<CityEntity> {
        return citiesResponse?.data?.items?.map { item ->
            CityEntity(
                id = item.id,
                name = item.name,
                countryName = item.country?.name ?: "Unavailable",
                countryCode = item.country?.code ?: "Unavailable",
                longitude = item.lng,
                latitude = item.lat,
                page = citiesResponse.data.pagination.currentPage
            )
        } ?: emptyList<CityEntity>()
    }
}
