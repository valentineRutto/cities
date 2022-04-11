package com.valentinerutto.cities.network.api

import com.valentinerutto.cities.network.model.CitiesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("connect/v1/city")
    suspend fun getCities(
        @Query("page") page: Int,
        @Query("include") include: String
    ): Response<CitiesResponse>

}
