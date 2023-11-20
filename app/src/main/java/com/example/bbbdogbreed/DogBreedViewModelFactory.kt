package com.example.bbbdogbreed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bbbdogbreed.domain.api.DogBreedRepository
import javax.inject.Inject

class DogBreedViewModelFactory @Inject constructor(private val dogbreedRepository: DogBreedRepository):
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DogBreedViewModel(dogbreedRepository) as T
    }
}