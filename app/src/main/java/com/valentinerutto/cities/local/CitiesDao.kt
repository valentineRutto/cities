package com.valentinerutto.cities.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CitiesDao {

    @Query("SELECT * FROM cities WHERE page LIKE :page")
    fun getCities(page: Int): List<CityEntity>

    @Query("SELECT * FROM cities WHERE name LIKE :cityName")
    suspend fun filterByCityName(cityName: String): List<CityEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveCities(cityEntities: List<CityEntity>)

}
