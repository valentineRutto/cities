package com.valentinerutto.cities.network.model


import com.google.gson.annotations.SerializedName

data class CitiesResponse(
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("time")
    val time: Int?
) {
    data class Data(
        @SerializedName("items")
        val items: List<Item>,
        @SerializedName("pagination")
        val pagination: Pagination
    ) {
        data class Item(
            @SerializedName("country_id")
            val countryId: Int,
            @SerializedName("created_at")
            val createdAt: String,
            @SerializedName("id")
            val id: Int,
            @SerializedName("lat")
            val lat: Double,
            @SerializedName("lng")
            val lng: Double,
            @SerializedName("local_name")
            val localName: String?,
            @SerializedName("name")
            val name: String,
            @SerializedName("updated_at")
            val updatedAt: String,
            @SerializedName("country")
            val country: CountryResponse?
        ) {
            data class CountryResponse(
                @SerializedName("id")
                val id: Int,
                @SerializedName("name")
                val name: String,
                @SerializedName("code")
                val code: String,
                @SerializedName("created_at")
                val createdAt: String,
                @SerializedName("updated_at")
                val updatedAt: String,
                @SerializedName("continent_id")
                val continentId: Int
            )
        }

        data class Pagination(
            @SerializedName("current_page")
            val currentPage: Int,
            @SerializedName("last_page")
            val lastPage: Int,
            @SerializedName("per_page")
            val perPage: Int,
            @SerializedName("total")
            val total: Int
        )
    }
}
