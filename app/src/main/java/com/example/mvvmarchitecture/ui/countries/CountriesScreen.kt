package com.example.mvvmarchitecture.ui.countries

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mvvmarchitecture.data.model.Country
import com.example.mvvmarchitecture.ui.base.UiState
import com.example.mvvmarchitecture.ui.base.showError
import com.example.mvvmarchitecture.ui.base.showLoading

@Composable
fun CountriesScreenRoute(
    onNewsClick: (countryCode: String) -> Unit,
    viewModel: CountryViewModel = hiltViewModel()
) {
    val countriesUiState: UiState<List<Country>> by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CountriesScreen(countriesUiState, onNewsClick)
    }
}

@Composable
fun CountriesScreen(
    countriesUiState: UiState<List<Country>>,
    onNewsClick: (countryCode: String) -> Unit
) {
    when (countriesUiState) {
        is UiState.Error -> {
            showError(text = countriesUiState.message)
        }

        is UiState.Loading -> {
            showLoading()
        }

        is UiState.Success -> {
            ShowCountryList(countriesUiState.data, onNewsClick)
        }
    }


}

@Composable
fun ShowCountryList(data: List<Country>, onNewsClick: (countryCode: String) -> Unit) {

    LazyColumn {
        items(data.size) { index ->
            showCountry(data[index], onNewsClick)

        }
    }
}

@Composable
fun showCountry(country: Country, onNewsClick: (countryCode: String) -> Unit) {
    Button(
        onClick = { country.code.let(onNewsClick) },
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier
            .padding(start = 20.dp, top = 5.dp, end = 20.dp, bottom = 5.dp)
            .fillMaxWidth(),
        contentPadding = PaddingValues(16.dp)
    ) {
        Text(
            text = country.name,
            color = Color.White,
            fontSize = 16.sp
        )
    }
}
