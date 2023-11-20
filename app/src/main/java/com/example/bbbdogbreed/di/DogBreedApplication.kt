package com.example.bbbdogbreed.di

import android.app.Application

class DogBreedApplication : Application() {

    lateinit var dogBreedComponent: DogBreedComponent

    override fun onCreate() {
        super.onCreate()
        dogBreedComponent = DaggerDogBreedComponent.builder().build()
    }
}