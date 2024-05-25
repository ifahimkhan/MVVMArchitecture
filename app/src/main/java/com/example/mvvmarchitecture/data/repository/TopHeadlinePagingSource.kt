package com.example.mvvmarchitecture.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mvvmarchitecture.data.api.NetworkService
import com.example.mvvmarchitecture.data.model.Article
import com.example.mvvmarchitecture.utils.AppConstant

class TopHeadlinePagingSource(private val networkService: NetworkService) :
    PagingSource<Int, Article>() {
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        return try {
            val page = params.key ?: AppConstant.Paging.INITIAL_PAGE

            val response = networkService.getTopHeadlines(
                country = AppConstant.DEFAULT_COUNTRY,
                page,
                AppConstant.Paging.PAGE_SIZE
            )
            LoadResult.Page(
                data = response.articles,
                prevKey = if (page == AppConstant.Paging.INITIAL_PAGE) null else page - 1,
                nextKey = if (response.articles.isEmpty()) null else page + 1,
            )
        }catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}