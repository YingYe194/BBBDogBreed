package com.example.bbbdogbreed.domain.api

import com.example.bbbdogbreed.domain.model.AllBreedsResponse
import com.example.bbbdogbreed.domain.model.DogImageList
import com.example.bbbdogbreed.domain.model.SingleDogImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject


class DogBreedRepository @Inject constructor(
    private val getDogBreedAPI: GetDogBreedAPI
) {

    suspend fun getAllBreedList(): AllBreedsResponse? {
        return withContext(Dispatchers.IO){
            getDogBreedAPI.listAllBreed().let{ response ->
                if(response.isSuccessful) response.body()
                else throw HttpException(response)
            }
        }
    }

    suspend fun getRandomBreed(): SingleDogImage? {
        return withContext(Dispatchers.IO){
            getDogBreedAPI.getRandomBreedImage().let{ response ->
                if(response.isSuccessful) response.body()
                else throw HttpException(response)
            }
        }
    }

    suspend fun getBreedImageList(breed: String): DogImageList? {
        return withContext(Dispatchers.IO){
            getDogBreedAPI.getBreedImageList(breed)
                .let{ response ->
                    if(response.isSuccessful) response.body()
                    else throw java.lang.Exception(response.message())
                }
        }
    }

    suspend fun getBreedSingleImage(breed: String): SingleDogImage? {
        return withContext(Dispatchers.IO){
            getDogBreedAPI.getSingleDogImage(breed)
                .let{ response ->
                    if(response.isSuccessful) response.body()
                    else throw java.lang.Exception(response.message())
                }
        }
    }
}