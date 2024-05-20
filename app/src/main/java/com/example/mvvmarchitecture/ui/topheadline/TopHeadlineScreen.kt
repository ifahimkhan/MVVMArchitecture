package com.example.mvvmarchitecture.ui.topheadline

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.mvvmarchitecture.data.model.Article
import com.example.mvvmarchitecture.data.model.Source
import com.example.mvvmarchitecture.ui.base.UiState
import com.example.mvvmarchitecture.ui.base.showError
import com.example.mvvmarchitecture.ui.base.showLoading
import com.example.mvvmarchitecture.utils.AppConstant


@Preview
@Composable
fun TopHeadLineScreenRoute(
    onNewsClick: (url: String) -> Unit,
    viewModel: TopHeadlineViewModel = hiltViewModel(),
    newsType: String = "",
    newsIdentifier: String = ""
) {
    val uiState: UiState<List<Article>> by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit,
        block = {
            when (newsType) {
                AppConstant.NewsBy.IntentParam.Value.COUNTRY -> {
                    viewModel.fetchNewsByCountry(newsIdentifier)
                }

                AppConstant.NewsBy.IntentParam.Value.SOURCE -> {
                    viewModel.fetchNewsBySource(newsIdentifier)
                }

                AppConstant.NewsBy.IntentParam.Value.LANGUAGE -> {
                    viewModel.fetchNewsByLanguage(newsIdentifier)
                }

                else -> {
                    viewModel.fetchTopHeadlines()
                }

            }
        })

    Column(modifier = Modifier.padding(4.dp)) {
        TopHeadLineScreen(uiState, onNewsClick)
    }

}

@Composable
fun TopHeadLineScreen(uiState: UiState<List<Article>>, onNewsClick: (url: String) -> Unit) {

    when (uiState) {
        is UiState.Error -> {
            showError(text = uiState.message)
        }

        is UiState.Loading -> showLoading()
        is UiState.Success -> {
            ShowArticleList(uiState.data, onNewsClick)
        }
    }
}

@Composable
fun ShowArticleList(data: List<Article>, onNewsClick: (url: String) -> Unit) {

    LazyColumn {
        items(data, key = { article -> article.url }) { article ->
            Article(article, onNewsClick)
        }
    }
}

@Composable
fun Article(article: Article, onNewsClick: (url: String) -> Unit) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .clickable {
            if (article.url.isNotEmpty()) {
                onNewsClick(article.url)
            }
        }) {
        BannerImage(article)
        TitleText(article.title)
        DescriptionText(article.description)
        SourceText(article.source)
    }
}

@Composable
fun SourceText(source: Source) {
    Text(
        text = source.name,
        style = MaterialTheme.typography.titleSmall,
        color = Color.Gray,
        maxLines = 1,
        modifier = Modifier.padding(4.dp, 4.dp, 4.dp, 8.dp)
    )
}
@Preview
@Composable
fun DescriptionText(description: String) {

    if (!description.isNullOrEmpty()) {
        Text(
            text = description,
            style = MaterialTheme.typography.titleSmall,
            color = Color.Gray,
            maxLines = 1,
            modifier = Modifier.padding(4.dp, 4.dp, 4.dp, 8.dp)
        )
    }
}

@Preview
@Composable
fun TitleText(title: String) {
    if (!title.isNullOrEmpty()) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = Color.Black,
            maxLines = 2,
            modifier = Modifier.padding(4.dp)
        )
    }
}

@Composable
fun BannerImage(article: Article) {
    AsyncImage(
        model = article.urlToImage, contentDescription = article.title,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .height(200.dp)
            .fillMaxWidth()
    )
}
