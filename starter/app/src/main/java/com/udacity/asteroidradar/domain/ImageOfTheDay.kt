package com.udacity.asteroidradar.domain

import com.squareup.moshi.Json

data class ImageOfTheDay(
    val url: String,
    val media_type: String,
    val title: String
)
