package com.example.mvvmarchitecture.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.mvvmarchitecture.R
import com.example.mvvmarchitecture.data.model.Article
import com.example.mvvmarchitecture.ui.base.TopAppBar
import com.example.mvvmarchitecture.ui.base.UiState
import com.example.mvvmarchitecture.ui.base.showError
import com.example.mvvmarchitecture.ui.base.showLoading
import com.example.mvvmarchitecture.ui.topheadline.ShowArticleList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreenRoute(
    onNewsClick: (query: String) -> Unit,
    viewModel: SearchViewModel = hiltViewModel(),
    navController: NavController
) {
    val uiState: UiState<List<Article>> by viewModel.uiState.collectAsStateWithLifecycle()
    var title = stringResource(id = R.string.screen_search_news)
    Scaffold(topBar = {
        TopAppBar(title = title,
            showBackArrow = true,
            onBackArrowClick = { navController.popBackStack() })
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Gray)
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var text by remember { mutableStateOf("") }
            var active by remember {
                mutableStateOf(false)
            }

            SearchBar(
                query = text,
                onQueryChange = {
                    text = it
                    viewModel.fetchNewsBySearch(text)
                },
                onSearch = {
                    active = false
                },
                placeholder = {
                    Text(text = "Search News:")
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        tint = MaterialTheme.colorScheme.surface,
                        contentDescription = null
                    )
                },
                trailingIcon = {},
                content = { SearchScreen(uiState = uiState, onNewsClick = onNewsClick) },
                active = active,
                onActiveChange = {
                    active = it
                },
                tonalElevation = 0.dp
            )


        }
    }

}

@Composable
fun SearchScreen(uiState: UiState<List<Article>>, onNewsClick: (query: String) -> Unit) {
    when (uiState) {
        is UiState.Error -> {
            showError(text = uiState.message)
        }

        is UiState.Loading -> showLoading()
        is UiState.Success -> ShowArticleList(uiState.data, onNewsClick)
    }
}

