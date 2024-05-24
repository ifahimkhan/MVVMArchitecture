package com.example.mvvmarchitecture.ui.topheadline

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmarchitecture.data.model.Article
import com.example.mvvmarchitecture.data.repository.TopHeadlineRepository
import com.example.mvvmarchitecture.ui.base.UiState
import com.example.mvvmarchitecture.utils.AppConstant.DEFAULT_COUNTRY
import com.example.mvvmarchitecture.utils.logger.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopHeadlineViewModel @Inject constructor(
    private val topHeadlineRepository: TopHeadlineRepository,
    private val logger: Logger
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Article>>>(UiState.Loading)

    val uiState: StateFlow<UiState<List<Article>>> = _uiState

    /* init {
         fetchTopHeadlines()
     }*/

    public fun fetchTopHeadlines() {
        viewModelScope.launch {
            topHeadlineRepository.getTopHeadlines(DEFAULT_COUNTRY)
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

    fun fetchNewsByLanguages(languageIds: List<String>) {
        var lang1 = languageIds[0].trim()
        var lang2 = languageIds[1].trim()
        logger.d(TopHeadlineViewModel::class.java, "lang1= $lang1 and lang2=$lang2")
        viewModelScope.launch(Dispatchers.IO) {
            topHeadlineRepository.getNewsByLanguage(lang1)
                .zip(topHeadlineRepository.getNewsByLanguage(lang2)) { result1, result2 ->
                    val articles = mutableListOf<Article>()
                    articles.addAll(result1)
                    articles.addAll(result2)
                    return@zip articles
                }
                .flowOn(Dispatchers.IO)
                .catch { error ->
                    _uiState.value = UiState.Error(error.toString())
                }.collect {
                    it.shuffle()
                    _uiState.value = UiState.Success(it)
                }
        }
    }


}