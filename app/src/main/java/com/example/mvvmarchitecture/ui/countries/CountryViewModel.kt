package com.example.mvvmarchitecture.ui.countries

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmarchitecture.data.model.Country
import com.example.mvvmarchitecture.data.repository.CountriesRepository
import com.example.mvvmarchitecture.ui.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountryViewModel @Inject constructor(private val countriesRepository: CountriesRepository) :
    ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Country>>>(UiState.Loading)

    val uiState: StateFlow<UiState<List<Country>>> = _uiState

    init {
        fetchCountries()
    }

    private fun fetchCountries() {
        viewModelScope.launch {
            countriesRepository.getCountries()
                .catch { e ->
                    _uiState.value = UiState.Error(e.toString())
                }
                .collect {
                    _uiState.value = UiState.Success(it)
                }
        }
    }

}