package com.example.mvvmarchitecture.ui.newssource

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.mvvmarchitecture.R
import com.example.mvvmarchitecture.data.model.Source
import com.example.mvvmarchitecture.ui.base.TopAppBar
import com.example.mvvmarchitecture.ui.base.UiState
import com.example.mvvmarchitecture.ui.base.showError
import com.example.mvvmarchitecture.ui.base.showLoading

@Composable
fun SourceScreenRoute(
    onNewsClick: (sourceId: String) -> Unit,
    viewModel: NewsSourceViewModel = hiltViewModel(),
    navController: NavController
) {
    val uiState: UiState<List<Source>> by viewModel.uiState.collectAsStateWithLifecycle()
    Scaffold(topBar = {
        TopAppBar(
            title = stringResource(id = R.string.screen_news_sources),
            showBackArrow = true,
            onBackArrowClick = { navController.popBackStack() }
        )
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            SourceScreen(uiState, onNewsClick)

        }
    }
}

@Composable
fun SourceScreen(uiState: UiState<List<Source>>, onNewsClick: (sourceId: String) -> Unit) {
    when (uiState) {
        is UiState.Error -> {
            showError(text = uiState.message)
        }

        is UiState.Loading -> {
            showLoading()
        }

        is UiState.Success -> {
            ShowSourceList(uiState.data, onNewsClick)
        }
    }
}

@Composable
fun ShowSourceList(data: List<Source>, onNewsClick: (sourceId: String) -> Unit) {
    LazyColumn {
        items(data.size) { index ->
            ShowSource(data[index], onNewsClick)
        }
    }
}

@Composable
fun ShowSource(source: Source, onNewsClick: (sourceId: String) -> Unit) {
    Button(
        onClick = { source.id?.let(onNewsClick) },
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier
            .padding(start = 20.dp, top = 5.dp, end = 20.dp, bottom = 5.dp)
            .fillMaxWidth(),
        contentPadding = PaddingValues(16.dp)
    ) {
        Text(
            text = source.name,
            color = Color.White,
            fontSize = 16.sp
        )
    }
}
