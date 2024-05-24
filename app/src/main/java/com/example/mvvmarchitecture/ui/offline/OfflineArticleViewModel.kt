package com.example.mvvmarchitecture.ui.offline

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmarchitecture.data.local.entity.ArticleEntity
import com.example.mvvmarchitecture.data.repository.TopHeadlineRepository
import com.example.mvvmarchitecture.ui.base.UiState
import com.example.mvvmarchitecture.utils.AppConstant
import com.example.mvvmarchitecture.utils.NetworkHelper
import com.example.mvvmarchitecture.utils.logger.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OfflineArticleViewModel @Inject constructor(
    private val topHeadlineRepository: TopHeadlineRepository,
    private val logger: Logger,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<UiState<List<ArticleEntity>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<ArticleEntity>>> = _uiState

    init {
        if (networkHelper.isNetworkAvailable()) {
            fetchNewsArticles()
        } else {
            fetchNewsArticleFromDb()
        }
    }

    private fun fetchNewsArticleFromDb() {
        viewModelScope.launch {
            topHeadlineRepository.getNewsArticleDirectFromDb(AppConstant.DEFAULT_COUNTRY)
                .flowOn(Dispatchers.IO)
                .catch { e -> _uiState.value = UiState.Error(e.toString()) }
                .collect {
                    _uiState.value = UiState.Success(it)
                }
        }
    }

    private fun fetchNewsArticles() {
        viewModelScope.launch {
            topHeadlineRepository.getNewsArticles(AppConstant.DEFAULT_COUNTRY)
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    _uiState.value = UiState.Error(e.toString())
                }.collect {
                    _uiState.value = UiState.Success(it)
                }
        }
    }


}