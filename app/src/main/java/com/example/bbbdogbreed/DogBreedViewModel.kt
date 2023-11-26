package com.example.bbbdogbreed

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bbbdogbreed.domain.model.AllBreed
import com.example.bbbdogbreed.domain.api.DogBreedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

const val ERROR_MESSAGE = "service call is not success"
// This was:
// @HiltViewModel
//class DogBreedViewModel(private val dogBreedRepository: DogBreedRepository) : ViewModel() {
@HiltViewModel
class DogBreedViewModel @Inject constructor(private val dogBreedRepository: DogBreedRepository) : ViewModel() {

    private val _randomDogImageViewState = MutableStateFlow(RandomDogImageViewState())
    val randomDogImageViewState: StateFlow<RandomDogImageViewState> =
        _randomDogImageViewState.asStateFlow()

    private val _dogBreedViewState = MutableStateFlow(DogBreedViewState())
    val dogBreedViewState: StateFlow<DogBreedViewState> = _dogBreedViewState.asStateFlow()

    init {
        fetchAllBreeds()
    }

    /**
     * This is to fetch all breeds, we can use returned all dog breeds response as an option in the dropdown list,
     * But running out of time, I don't have it converted
     */
    private fun fetchAllBreeds() {
        viewModelScope.launch {
            runCatching {
                _dogBreedViewState.update { state -> state.copy(isLoading = true, isError = false) }
                dogBreedRepository.getAllBreedList()
            }.onSuccess { response ->
                if (response?.status == "success") {
                    _dogBreedViewState.update { state ->
                        state.copy(
                            isLoading = false,
                            isError = false,
                            allDogBreedList = response.breedAndAllBreed
                        )
                    }
                } else {
                    Log.i("info", ERROR_MESSAGE)
                    _dogBreedViewState.update { state ->
                        state.copy(
                            isLoading = false,
                            isError = false,
                            allDogBreedList = null,
                            fetchedDogBreedImageList = null
                        )
                    }
                }
            }.onFailure { throwable ->
                _dogBreedViewState.update { state ->
                    state.copy(
                        isLoading = false,
                        isError = true,
                        allDogBreedList = null,
                        fetchedDogBreedImageList = null
                    )
                }
                Log.e("error", throwable.message.orEmpty())
            }
        }
    }

    fun showRandomImage() {
        viewModelScope.launch {
            runCatching {
                _randomDogImageViewState.update { state ->
                    state.copy(
                        isLoading = true,
                        isError = false
                    )
                }
                dogBreedRepository.getRandomBreed()
            }.onSuccess { response ->
                if (response?.status == "success") {
                    _randomDogImageViewState.update { state ->
                        state.copy(
                            isLoading = false,
                            isError = false,
                            randomImage = response.dogImage
                        )
                    }
                } else {
                    Log.i("info", ERROR_MESSAGE)
                    _randomDogImageViewState.update { state ->
                        state.copy(
                            isLoading = false,
                            isError = false,
                            randomImage = null
                        )
                    }
                }
            }.onFailure { throwable ->
                _randomDogImageViewState.update { state ->
                    state.copy(
                        isLoading = false,
                        isError = true,
                        randomImage = null
                    )
                }
                Log.e("error", throwable.message.orEmpty())
            }
        }
    }

    internal fun fetchDogImageList(selectedBreed: String) {
        viewModelScope.launch {
            runCatching {
                _dogBreedViewState.update { state -> state.copy(isLoading = true, isError = false) }
                dogBreedRepository.getBreedImageList(selectedBreed)
            }.onSuccess { response ->
                if (response?.status == "success") {
                    _dogBreedViewState.update { state ->
                        state.copy(
                            isLoading = false,
                            isError = false,
                            errorMessage = null,
                            fetchedDogBreedImageList = response.breedImageList
                        )
                    }
                } else {
                    Log.i("info", ERROR_MESSAGE)
                    _dogBreedViewState.update { state ->
                        state.copy(
                            isLoading = false,
                            isError = true,
                            errorMessage = ERROR_MESSAGE,
                            fetchedDogBreedImageList = null
                        )
                    }
                }
            }.onFailure { throwable ->
                _dogBreedViewState.update { state ->
                    state.copy(
                        isLoading = false,
                        isError = true,
                        errorMessage = throwable.message.orEmpty(),
                        fetchedDogBreedImageList = null
                    )
                }
                Log.e("error", throwable.message.orEmpty())
            }
        }
    }


    internal fun fetchDogSingleImage(selectedBreed: String) {
        viewModelScope.launch {
            runCatching {
                _dogBreedViewState.update { state -> state.copy(isLoading = true, isError = false) }
                dogBreedRepository.getBreedSingleImage(selectedBreed)
            }.onSuccess { response ->
                if (response?.status == "success") {
                    _dogBreedViewState.update { state ->
                        state.copy(
                            isLoading = false,
                            isError = false,
                            errorMessage = null,
                            fetchedDogBreedSingleImage = response.dogImage,
                        )
                    }
                } else {
                    Log.i("info", ERROR_MESSAGE)
                    _dogBreedViewState.update { state ->
                        state.copy(
                            isLoading = false,
                            isError = true,
                            errorMessage = ERROR_MESSAGE,
                            fetchedDogBreedSingleImage = null
                        )
                    }
                }
            }.onFailure { throwable ->
                _dogBreedViewState.update { state ->
                    state.copy(
                        isLoading = false,
                        isError = true,
                        errorMessage = throwable.message.orEmpty(),
                        fetchedDogBreedSingleImage = null
                    )
                }
                Log.e("error", throwable.message.orEmpty())
            }
        }
    }

    //Reset view state to clean result UI layout
    internal fun clearViewState() {
        _dogBreedViewState.update { state ->
            state.copy(
                isError = false,
                errorMessage = null,
                fetchedDogBreedImageList = null,
                fetchedDogBreedSingleImage = null
            )
        }
    }

    data class RandomDogImageViewState(
        val isLoading: Boolean = false,
        val isError: Boolean = false,
        val randomImage: String? = null
    )

    data class DogBreedViewState(
        val isLoading: Boolean = false,
        val isError: Boolean = false,
        val errorMessage: String? = null,
        val allDogBreedList: AllBreed? = null,
        val fetchedDogBreedImageList: List<String>? = null,
        val fetchedDogBreedSingleImage: String? = null
    )
}