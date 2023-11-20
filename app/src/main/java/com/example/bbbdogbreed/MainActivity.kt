package com.example.bbbdogbreed

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.example.bbbdogbreed.ui.theme.BBBDogBreedTheme
import javax.inject.Inject
import androidx.compose.runtime.getValue
import com.example.bbbdogbreed.di.DogBreedApplication
import com.example.bbbdogbreed.ui.MainContent

class MainActivity : ComponentActivity() {

    private lateinit var dogBreedViewModel: DogBreedViewModel

    @Inject
    lateinit var dogBreedViewModelFactory: DogBreedViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (application as DogBreedApplication).dogBreedComponent.inject(this)

        dogBreedViewModel =
            ViewModelProvider(this, dogBreedViewModelFactory)[DogBreedViewModel::class.java]

        setContent {
            val dogBreedViewState by dogBreedViewModel.dogBreedViewState.collectAsState()
            val randomDogImageViewState by dogBreedViewModel.randomDogImageViewState.collectAsState()

            BBBDogBreedTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainContent(
                        dogBreedViewState = dogBreedViewState,
                        randomDogImageViewState = randomDogImageViewState,
                        modifier = Modifier.padding(16.dp),
                        onClickRandomImageButtonAction = { dogBreedViewModel.showRandomImage() },
                        onFetchImageList = { selectedBreed ->
                            dogBreedViewModel.fetchDogImageList(selectedBreed)
                        },
                        onFetchSingleImage = { selectedBreed ->
                            dogBreedViewModel.fetchDogSingleImage(selectedBreed)
                        },
                        clearViewStateAction = {
                            dogBreedViewModel.clearViewState()
                        }
                    )
                }
            }
        }
    }
}