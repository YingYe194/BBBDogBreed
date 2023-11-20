package com.example.bbbdogbreed.ui

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.bbbdogbreed.DogBreedViewModel
import com.example.bbbdogbreed.R
import com.example.bbbdogbreed.domain.model.AllBreeds
import com.example.bbbdogbreed.ui.theme.BBBDogBreedTheme

@Composable
fun MainContent(
    dogBreedViewState: DogBreedViewModel.DogBreedViewState,
    randomDogImageViewState: DogBreedViewModel.RandomDogImageViewState,
    modifier: Modifier = Modifier,
    onClickRandomImageButtonAction: () -> Unit,
    onFetchImageList: (String) -> Unit,
    onFetchSingleImage: (String) -> Unit,
    clearViewStateAction: () -> Unit = {}
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = stringResource(R.string.welcomed_to_this_app),
        )
        val scrollState = rememberScrollState()
        var index by remember { mutableIntStateOf(-1) }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .horizontalScroll(scrollState)
        ) {
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                index = 0
                onClickRandomImageButtonAction()
            }) {
                Text(stringResource(R.string.random_image))
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                index = 1
                clearViewStateAction()
            }) {
                Text(stringResource(R.string.input_breed_button))
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                index = 2
                clearViewStateAction()
            }) {
                Text(stringResource(R.string.select_breed_button))
            }
        }

        when (index) {
            0 -> RandomDogImageContent(randomDogImageViewState)

            1 ->
                InputDogBreedContent(
                    dogBreedViewState = dogBreedViewState,
                    fetchImageList = onFetchImageList,
                    fetchSingleImage = onFetchSingleImage
                )

            2 ->
                SelectDogBreedDropDownMenuContent(
                    dogBreedViewState = dogBreedViewState,
                    fetchImageList = onFetchImageList,
                    fetchSingleImage = onFetchSingleImage
                )
        }
    }
}

@Composable
private fun RandomDogImageContent(
    randomDogImageViewState: DogBreedViewModel.RandomDogImageViewState,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
        when {
            randomDogImageViewState.isLoading ->
                CircularProgressIndicator(
                    modifier = Modifier.size(64.dp),
                    color = MaterialTheme.colorScheme.primary
                )

            randomDogImageViewState.isError -> {
                Text(stringResource(R.string.error_message))
            }

            else -> {
                AsyncImage(
                    modifier = Modifier.fillMaxWidth(),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(randomDogImageViewState.randomImage)
                        .error(R.drawable.baseline_error_24)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

@Composable
private fun InputDogBreedContent(
    dogBreedViewState: DogBreedViewModel.DogBreedViewState,
    modifier: Modifier = Modifier,
    fetchImageList: (String) -> Unit,
    fetchSingleImage: (String) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth()
    ) {
        var selectedBreed by remember { mutableStateOf("") }

        Text(stringResource(R.string.input_field_header))
        TextField(
            value = selectedBreed,
            onValueChange = { selectedBreed = it },
            label = { Text(stringResource(R.string.dog_breed)) }
        )
        FetchDogImageContent(
            dogBreedViewState = dogBreedViewState,
            selectedBreed = selectedBreed,
            fetchImageList = fetchImageList,
            fetchSingleImage = fetchSingleImage
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SelectDogBreedDropDownMenuContent(
    dogBreedViewState: DogBreedViewModel.DogBreedViewState,
    modifier: Modifier = Modifier,
    fetchImageList: (String) -> Unit,
    fetchSingleImage: (String) -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth()
    ) {
        val dogBreeds = AllBreeds
        var expanded by remember { mutableStateOf(false) }
        var selectedBreed by remember { mutableStateOf(dogBreeds[0]) }

        Text(
            text = stringResource(R.string.drop_down_menu_header)
        )
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = {
                    expanded = !expanded
                },
                modifier = Modifier.padding(top = 8.dp)
            ) {
                TextField(
                    value = selectedBreed,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier.menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    dogBreeds.forEach { item ->
                        DropdownMenuItem(
                            text = { Text(text = item) },
                            onClick = {
                                selectedBreed = item
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
        FetchDogImageContent(
            dogBreedViewState = dogBreedViewState,
            selectedBreed = selectedBreed,
            fetchImageList = fetchImageList,
            fetchSingleImage = fetchSingleImage
        )
    }

}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun FetchDogImageContent(
    dogBreedViewState: DogBreedViewModel.DogBreedViewState,
    selectedBreed: String,
    modifier: Modifier = Modifier,
    fetchImageList: (String) -> Unit,
    fetchSingleImage: (String) -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        var isShowSingleImage by remember { mutableStateOf(false) }
        val keyboardController = LocalSoftwareKeyboardController.current
        Button(
            modifier = Modifier.padding(top = 8.dp),
            onClick = {
                isShowSingleImage = false
                fetchImageList(selectedBreed)
                keyboardController?.hide()
            }
        ) {
            Text(stringResource(R.string.fetch_dog_list))
        }

        Button(
            modifier = Modifier.padding(top = 8.dp),
            onClick = {
                isShowSingleImage = true
                fetchSingleImage(selectedBreed)
                keyboardController?.hide()
            }
        ) {
            Text(stringResource(R.string.fetch_dog_single_image))
        }

        ResultDogBreedImageContent(
            dogBreedViewState = dogBreedViewState,
            isShowSingleImage = isShowSingleImage,
            selectedBreed = selectedBreed,
            modifier = Modifier.padding(top = 8.dp),
        )
    }
}

@Composable
private fun ResultDogBreedImageContent(
    dogBreedViewState: DogBreedViewModel.DogBreedViewState,
    isShowSingleImage: Boolean,
    selectedBreed: String,
    modifier: Modifier = Modifier
) {
    when {
        dogBreedViewState.isLoading ->
            CircularProgressIndicator(
                modifier = Modifier.size(64.dp),
                color = MaterialTheme.colorScheme.primary
            )

        dogBreedViewState.isError -> {
            when{
                selectedBreed.isEmpty() -> {
                    Text(stringResource(R.string.empty_error_message))
                }
                dogBreedViewState.errorMessage.isNullOrEmpty() -> {
                    Text(stringResource(R.string.error_message))
                }
                else -> {
                    Text(dogBreedViewState.errorMessage)
                }
            }
        }

        else -> {
            if (!isShowSingleImage) {
                val imageResourceList = dogBreedViewState.fetchedDogBreedImageList
                if (!imageResourceList.isNullOrEmpty()) {
                    Text(stringResource(R.string.scrollable_image_list_header))

                    LazyColumn(
                        modifier = modifier
                    ) {
                        items(imageResourceList) { imageResource ->
                            AsyncImage(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 8.dp),
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(imageResource)
                                    .error(R.drawable.baseline_error_24)
                                    .build(),
                                contentDescription = null,
                                contentScale = ContentScale.Crop
                            )

                        }
                    }
                }
            } else {
                Column(
                    modifier = modifier.verticalScroll(rememberScrollState())
                ) {
                    AsyncImage(
                        modifier = Modifier.fillMaxWidth(),
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(dogBreedViewState.fetchedDogBreedSingleImage)
                            .error(R.drawable.baseline_error_24)
                            .build(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainContentPreview() {
    BBBDogBreedTheme {
        MainContent(
            dogBreedViewState = DogBreedViewModel.DogBreedViewState(isLoading = false),
            randomDogImageViewState = DogBreedViewModel.RandomDogImageViewState(),
            onClickRandomImageButtonAction = {},
            onFetchImageList = {},
            onFetchSingleImage = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingSpinnerPreview() {
    BBBDogBreedTheme {
        MainContent(
            dogBreedViewState = DogBreedViewModel.DogBreedViewState(isLoading = true),
            randomDogImageViewState = DogBreedViewModel.RandomDogImageViewState(),
            onClickRandomImageButtonAction = {},
            onFetchImageList = {},
            onFetchSingleImage = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun InputDogBreedContentPreview() {
    BBBDogBreedTheme {
        InputDogBreedContent(
            dogBreedViewState = DogBreedViewModel.DogBreedViewState(
                fetchedDogBreedSingleImage = "https://images.dog.ceo/breeds/hound-afghan/n02088094_1003.jpg"
            ),
            fetchImageList = {},
            fetchSingleImage = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SelectDogBreedDropDownMenuContentPreview() {
    BBBDogBreedTheme {
        SelectDogBreedDropDownMenuContent(
            dogBreedViewState = DogBreedViewModel.DogBreedViewState(
                fetchedDogBreedSingleImage = "https://images.dog.ceo/breeds/hound-afghan/n02088094_1003.jpg"
            ),
            fetchImageList = {},
            fetchSingleImage = {}
        )
    }
}