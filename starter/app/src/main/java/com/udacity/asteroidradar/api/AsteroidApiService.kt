package com.udacity.asteroidradar.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.nasa.gov/"
private const val NASA_API_KEY = "r17td9v9Sf83wXKePCJC1C2bF7te1l9S2HK4gAxO"
private const val API_KEY_QUERY_NAME = "api_key"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface AsteroidApiService {


    @GET("neo/rest/v1/feed")
    suspend fun getAsteroidFeed(
        @Query("start_date") startDate: String, @Query("end_date") endDate: String? = null, @Query(
            API_KEY_QUERY_NAME
        ) apiKey: String = NASA_API_KEY
    ): String

    @GET("planetary/apod")
    suspend fun getImageOfTheDay(@Query(API_KEY_QUERY_NAME) apiKey: String = NASA_API_KEY): String

}

object AsteroidApi {

    val asteroidService: AsteroidApiService by lazy { retrofit.create(AsteroidApiService::class.java) }

}