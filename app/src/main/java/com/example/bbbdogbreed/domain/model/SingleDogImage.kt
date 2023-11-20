package com.example.bbbdogbreed.domain.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SingleDogImage(
    /**
     * Single dog image resource url
     */
    @Json(name = "message")
    var dogImage: String,

    @Json(name="status")
    var status: String
)