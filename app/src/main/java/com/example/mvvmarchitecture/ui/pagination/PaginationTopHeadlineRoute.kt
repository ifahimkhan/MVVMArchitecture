package com.example.mvvmarchitecture.ui.pagination

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.mvvmarchitecture.R
import com.example.mvvmarchitecture.data.model.Article
import com.example.mvvmarchitecture.ui.base.TopAppBar
import com.example.mvvmarchitecture.ui.base.showError
import com.example.mvvmarchitecture.ui.base.showLoading
import com.example.mvvmarchitecture.ui.topheadline.BannerImage
import com.example.mvvmarchitecture.ui.topheadline.DescriptionText
import com.example.mvvmarchitecture.ui.topheadline.SourceText
import com.example.mvvmarchitecture.ui.topheadline.TitleText

@Composable
fun PaginationTopHeadlineRoute(
    onNewsClick: (url: String) -> Unit,
    navController: NavController,
    viewModel: PaginationTopHeadlineViewModel = hiltViewModel()
) {
    val topHeadlineUiState = viewModel.topHeadlineUiState.collectAsLazyPagingItems()

    Scaffold(topBar = {
        TopAppBar(
            title = stringResource(id = R.string.screen_top_headline_pagination),
            showBackArrow = true
        )
        { navController.popBackStack() }
    }) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            TopHeadlineScreen(topHeadlineUiState, onNewsClick)
        }
    }

}

@Composable
fun TopHeadlineScreen(
    articles: LazyPagingItems<Article>,
    onNewsClick: (url: String) -> Unit
) {
    ArticleList(articles, onNewsClick)
    articles.apply {
        when {
            loadState.refresh is LoadState.Loading -> {
                showLoading()
            }

            loadState.refresh is LoadState.Error ->{
                val error = articles.loadState.refresh as LoadState.Error
                error.error.localizedMessage?.let { showError(text = it) }
            }

            loadState.append is LoadState.Loading -> {
                showLoading()
            }

            loadState.append is LoadState.Error -> {
                val error = articles.loadState.append as LoadState.Error
                showError(error.error.localizedMessage!!)
            }
        }
    }

}

@Composable
fun ArticleList(articles: LazyPagingItems<Article>, onNewsClick: (url: String) -> Unit) {

    LazyColumn {
        items(articles.itemCount, key = { index -> articles[index]!!.url }) { index ->
            ApiArticle(articles[index]!!, onNewsClick)
        }
    }
}

@Composable
fun ApiArticle(article: Article, onNewsClick: (url: String) -> Unit) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .shadow(4.dp)
            .padding(6.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .clickable {
                if (article.url.isNotEmpty()) {
                    onNewsClick(article.url)
                }
            }) {

            BannerImage(imageUrl = article.urlToImage, title = article.title)
            TitleText(article.title)
            DescriptionText(article.description)
            SourceText(article.source.name)
        }
    }

}
