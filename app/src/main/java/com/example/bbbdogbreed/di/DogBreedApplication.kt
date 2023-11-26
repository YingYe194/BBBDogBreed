package com.example.bbbdogbreed.di

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class DogBreedApplication : Application() {

    lateinit var dogBreedComponent: DogBreedComponent

    override fun onCreate() {
        super.onCreate()
        dogBreedComponent = DaggerDogBreedComponent.builder().build()
    }
}