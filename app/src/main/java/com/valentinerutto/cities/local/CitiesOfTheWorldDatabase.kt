package com.valentinerutto.cities.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(exportSchema = false, version = 1, entities = [CityEntity::class])
abstract class CitiesOfTheWorldDatabase : RoomDatabase() {

    abstract val citiesDao: CitiesDao
}
