package com.udacity.asteroidradar.repository

import androidx.lifecycle.LiveData
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.api.parseImageOfTheDayJson
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.domain.AsteroidFilter
import com.udacity.asteroidradar.domain.ImageOfTheDay
import kotlinx.coroutines.*
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class AsteroidRepository(private val asteroidDatabase: AsteroidDatabase) {


    val asteroids: LiveData<List<Asteroid>> = asteroidDatabase.asteroidDao.getAsteroids()

    suspend fun getImageOfTheDay(): ImageOfTheDay? {
        var imageOfTheDay: ImageOfTheDay?
        withContext(Dispatchers.IO) {
            val imageOfTheDayJson = AsteroidApi.asteroidService.getImageOfTheDay()
            imageOfTheDay = parseImageOfTheDayJson(JSONObject(imageOfTheDayJson))
        }
        return imageOfTheDay
    }

    suspend fun getAsteroidList(startDate: Date, endDate: Date?, filter: AsteroidFilter){
        val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
        val startDateQueryString = dateFormat.format(startDate)
        var endDateQueryString: String? = null
        endDate?.let { date->
            endDateQueryString = dateFormat.format(date)
        }
        val asteroidsJson = AsteroidApi.asteroidService.getAsteroidFeed(startDateQueryString, endDateQueryString)
        val asteroidsList = parseAsteroidsJsonResult(JSONObject(asteroidsJson), filter)
        withContext(Dispatchers.IO){
            asteroidDatabase.asteroidDao.insertAll(asteroidsList)
        }
    }

}