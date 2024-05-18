package com.example.mvvmarchitecture.ui.language

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmarchitecture.data.model.Language
import com.example.mvvmarchitecture.data.repository.LanguageRepository
import com.example.mvvmarchitecture.ui.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LanguageViewModel @Inject constructor(private val languageRepository: LanguageRepository) :
    ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Language>>>(UiState.Loading)

    val uiState: StateFlow<UiState<List<Language>>> = _uiState

    init {
        fetchLanguages()
        Log.e(LanguageViewModel::class.java.name, "init called")

    }

    private fun fetchLanguages() {
        viewModelScope.launch {
            languageRepository.getLanguages()
                .catch { e ->
                    _uiState.value = UiState.Error(e.toString())
                }
                .collect {
                    _uiState.value = UiState.Success(it)
                }
        }
    }

}