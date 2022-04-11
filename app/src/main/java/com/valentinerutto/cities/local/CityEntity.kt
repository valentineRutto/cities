package com.valentinerutto.cities.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cities")
data class CityEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val countryName: String,
    val countryCode: String,
    val longitude: Double,
    val latitude: Double,
    val page: Int
)
