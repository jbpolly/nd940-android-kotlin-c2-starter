package com.udacity.asteroidradar.main

import android.accounts.NetworkErrorException
import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.repository.AsteroidRepository
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.domain.AsteroidFilter
import com.udacity.asteroidradar.domain.ImageOfTheDay
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.util.*

class MainViewModel(val app: Application, private val asteroidRepository: AsteroidRepository) :
    AndroidViewModel(app) {

    val imageOfTheDay = MutableLiveData<ImageOfTheDay>()
    val asteroidList = asteroidRepository.asteroids

    private val _navigateToDetails = MutableLiveData<Asteroid>()
    val navigateToDetails: LiveData<Asteroid>
        get() = _navigateToDetails

    val errorRetrievingData = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    init {
        errorRetrievingData.value = false
        loading.value = false
        requestImageOfTheDay()
        requestAsteroidList(AsteroidFilter.WEEK)
    }

    private fun requestImageOfTheDay() {
        viewModelScope.launch {
            loading.value = true
            try {
                imageOfTheDay.value = asteroidRepository.getImageOfTheDay()
            } catch (e: Exception) {
                Log.d("TAG", "error")
            }
            loading.value = false
        }
    }

    fun requestAsteroidList(filter: AsteroidFilter = AsteroidFilter.WEEK) {
        viewModelScope.launch {
            try {
                when(filter){
                    AsteroidFilter.DAY ->   asteroidRepository.getAsteroidList(Date(), Date(), filter)
                    AsteroidFilter.WEEK ->  asteroidRepository.getAsteroidList(Date(), null, filter)
                    AsteroidFilter.SAVED -> {
                        //do nothing
                    }
                }
                errorRetrievingData.value = false
            } catch (e: Exception) {
                errorRetrievingData.value = true
            }
        }
    }

    fun navigatedToDetails(){
        _navigateToDetails.value = null
    }

    fun onAsteroidClicked(asteroid: Asteroid) {
        //go to details
        _navigateToDetails.value = asteroid

    }

    fun updateFilter(filter: AsteroidFilter) {
        requestAsteroidList(filter)
    }


}