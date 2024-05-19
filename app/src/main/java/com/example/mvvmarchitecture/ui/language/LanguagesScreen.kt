package com.example.mvvmarchitecture.ui.language

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
import com.example.mvvmarchitecture.data.model.Language
import com.example.mvvmarchitecture.ui.base.UiState
import com.example.mvvmarchitecture.ui.base.showError
import com.example.mvvmarchitecture.ui.base.showLoading

@Composable
fun LanguagesScreenRoute(
    onNewsClick: (languageId: String) -> Unit,
    viewModel: LanguageViewModel = hiltViewModel()
) {
    val uiState: UiState<List<Language>> by viewModel.uiState.collectAsStateWithLifecycle()
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LanguagesScreen(uiState, onNewsClick)
    }

}

@Composable
fun LanguagesScreen(uiState: UiState<List<Language>>, onNewsClick: (languageId: String) -> Unit) {
    when (uiState) {
        is UiState.Error -> {
            showError(text = uiState.message)
        }

        is UiState.Loading -> {
            showLoading()
        }

        is UiState.Success -> {
            showLanguageList(uiState.data, onNewsClick)
        }
    }

}

@Composable
fun showLanguageList(data: List<Language>, onNewsClick: (languageId: String) -> Unit) {
    LazyColumn {
        items(data.size) { index ->
            showLanguage(data[index], onNewsClick)

        }
    }
}

@Composable
fun showLanguage(language: Language, onNewsClick: (languageId: String) -> Unit) {
    Button(
        onClick = { language.code.let(onNewsClick) },
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier
            .padding(start = 20.dp, top = 5.dp, end = 20.dp, bottom = 5.dp)
            .fillMaxWidth(),
        contentPadding = PaddingValues(16.dp)
    ) {
        Text(
            text = language.name,
            color = Color.White,
            fontSize = 16.sp
        )
    }
}
