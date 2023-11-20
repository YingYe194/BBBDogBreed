package com.example.bbbdogbreed.domain.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DogImageList(
    /**
     * List of dog image resource url
     */
    @Json(name = "message")
    var breedImageList: List<String>,

    @Json(name="status")
    var status: String
)