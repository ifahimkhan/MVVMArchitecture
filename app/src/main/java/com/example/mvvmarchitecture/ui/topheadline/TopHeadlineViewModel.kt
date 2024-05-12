package com.example.mvvmarchitecture.ui.topheadline

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmarchitecture.data.model.Article
import com.example.mvvmarchitecture.data.repository.TopHeadlineRepository
import com.example.mvvmarchitecture.ui.base.UiState
import com.example.mvvmarchitecture.utils.AppConstant.COUNTRY
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class TopHeadlineViewModel(private val topHeadlineRepository: TopHeadlineRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Article>>>(UiState.Loading)

    val uiState: StateFlow<UiState<List<Article>>> = _uiState

    /* init {
         fetchTopHeadlines()
     }*/

    public fun fetchTopHeadlines() {
        viewModelScope.launch {
            topHeadlineRepository.getTopHeadlines(COUNTRY)
                .catch { e ->
                    _uiState.value = UiState.Error(e.toString())
                }
                .collect {
                    _uiState.value = UiState.Success(it)
                }
        }
    }

    public fun fetchNewsByCountry(country: String) {
        viewModelScope.launch {
            topHeadlineRepository.getNewsByCountry(country)
                .catch { e ->
                    _uiState.value = UiState.Error(e.toString())
                }
                .collect {
                    _uiState.value = UiState.Success(it)
                }
        }
    }

    public fun fetchNewsBySource(source: String) {
        viewModelScope.launch {
            topHeadlineRepository.getNewsBySource(source)
                .catch { e ->
                    _uiState.value = UiState.Error(e.toString())
                }
                .collect {
                    _uiState.value = UiState.Success(it)
                }
        }
    }

    fun fetchNewsByLanguage(languageId: String) {
        viewModelScope.launch {
            topHeadlineRepository.getNewsByLanguage(languageId)
                .catch { e ->
                    _uiState.value = UiState.Error(e.toString())
                }.collect {
                    _uiState.value = UiState.Success(it)
                }
        }
    }


}