package com.example.mvvmarchitecture.ui.newssource

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmarchitecture.data.model.Source
import com.example.mvvmarchitecture.data.repository.NewsSourcesRepository
import com.example.mvvmarchitecture.ui.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsSourceViewModel @Inject constructor(private val newsSourcesRepository: NewsSourcesRepository) :
    ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Source>>>(UiState.Loading)

    val uiState: StateFlow<UiState<List<Source>>> = _uiState

    init {
        fetchNewsSources()
    }

    private fun fetchNewsSources() {
        viewModelScope.launch {
            newsSourcesRepository.getNewsSource()
                .catch { e ->
                    _uiState.value = UiState.Error(e.toString())
                }
                .collect {
                    _uiState.value = UiState.Success(it)
                }
        }
    }

}