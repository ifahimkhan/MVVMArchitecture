package com.example.mvvmarchitecture.ui.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmarchitecture.data.model.Article
import com.example.mvvmarchitecture.data.model.Language
import com.example.mvvmarchitecture.data.model.TopHeadlineResponse
import com.example.mvvmarchitecture.data.repository.LanguageRepository
import com.example.mvvmarchitecture.data.repository.TopHeadlineRepository
import com.example.mvvmarchitecture.ui.base.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class SearchViewModel(private val topHeadlineRepository: TopHeadlineRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Article>>>(UiState.Loading)

    val uiState: StateFlow<UiState<List<Article>>> = _uiState

    var search = MutableStateFlow<String>("")

    init {
        fetchNewsBySearch(search.value)
        Log.e(SearchViewModel::class.java.name, "init called")

    }

    public fun fetchNewsBySearch(query:String) {
        viewModelScope.launch {
            topHeadlineRepository.getNewsBySearch(query)
                .catch { e ->
                    _uiState.value = UiState.Error(e.toString())
                }
                .collect {
                    _uiState.value = UiState.Success(it)
                }
        }
    }

}