package com.example.bbbdogbreed.di

import com.example.bbbdogbreed.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface DogBreedComponent {

    fun inject(mainActivity: MainActivity) // for field inject property inside the MainActivity
}