package com.example.mvvmarchitecture.ui.offline

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.mvvmarchitecture.R
import com.example.mvvmarchitecture.data.local.entity.ArticleEntity
import com.example.mvvmarchitecture.ui.base.TopAppBar
import com.example.mvvmarchitecture.ui.base.UiState
import com.example.mvvmarchitecture.ui.base.showError
import com.example.mvvmarchitecture.ui.base.showLoading
import com.example.mvvmarchitecture.ui.topheadline.BannerImage
import com.example.mvvmarchitecture.ui.topheadline.DescriptionText
import com.example.mvvmarchitecture.ui.topheadline.SourceText
import com.example.mvvmarchitecture.ui.topheadline.TitleText


@Composable
fun OfflineArticleRoute(
    onNewsClick: (url: String) -> Unit,
    viewModel: OfflineArticleViewModel = hiltViewModel(),
    navController: NavController
) {
    val uiState: UiState<List<ArticleEntity>> by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(topBar = {
        TopAppBar(
            title = stringResource(id = R.string.screen_offline_article),
            showBackArrow = true,
            onBackArrowClick = { navController.popBackStack() }
        )
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            TopHeadLineOfflineScreen(uiState = uiState, onNewsClick = onNewsClick)

        }
    }

}

@Composable
fun TopHeadLineOfflineScreen(
    uiState: UiState<List<ArticleEntity>>,
    onNewsClick: (url: String) -> Unit
) {
    when (uiState) {
        is UiState.Error -> showError(text = uiState.message)
        is UiState.Loading -> showLoading()
        is UiState.Success -> {
            OfflineArticleList(uiState.data, onNewsClick)
        }
    }
}

@Composable
fun OfflineArticleList(data: List<ArticleEntity>, onNewsClick: (url: String) -> Unit) {
    LazyColumn {
        items(data, key = { entity -> entity.url }) { entity ->
            ShowArticle(entity, onNewsClick)
        }
    }
}

@Composable
fun ShowArticle(article: ArticleEntity, onNewsClick: (url: String) -> Unit) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .clickable {
            if (article.url.isNotEmpty()) {
                onNewsClick(article.url)
            }
        }) {
        BannerImage(article.imageUrl, article.title)
        TitleText(article.title)
        article.description?.let { DescriptionText(it) }
        article.source.name?.let { SourceText(it) }
    }

}
