package com.example.bbbdogbreed.domain.api

import com.example.bbbdogbreed.domain.model.AllBreedsResponse
import com.example.bbbdogbreed.domain.model.DogImageList
import com.example.bbbdogbreed.domain.model.SingleDogImage
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GetDogBreedAPI {

    @GET("breeds/list/all")
    suspend fun listAllBreed(): Response<AllBreedsResponse>

    @GET("breeds/image/random")
    suspend fun getRandomBreedImage(): Response<SingleDogImage>

    @GET("breed/{breed}/images")
    suspend fun getBreedImageList(@Path("breed") breed: String): Response<DogImageList>

    @GET("breed/{breed}/images/random")
    suspend fun getSingleDogImage(@Path("breed") breed: String): Response<SingleDogImage>
}