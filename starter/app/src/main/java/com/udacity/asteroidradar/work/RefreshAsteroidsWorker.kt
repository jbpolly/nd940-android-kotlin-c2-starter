package com.udacity.asteroidradar.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.domain.AsteroidFilter
import com.udacity.asteroidradar.repository.AsteroidRepository
import retrofit2.HttpException
import java.util.*

class RefreshAsteroidsWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        val database = AsteroidDatabase.getInstance(applicationContext)
        val repository = AsteroidRepository(database)
        return try {
            repository.deleteAllAsteroidsFromDb()
            repository.getAsteroidList(Date(), null, AsteroidFilter.WEEK)
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }


    companion object {
        const val WORKER_NAME = "AsteroidRefresher"
    }
}