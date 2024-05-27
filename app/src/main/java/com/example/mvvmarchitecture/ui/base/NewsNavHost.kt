package com.example.mvvmarchitecture.ui.base

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mvvmarchitecture.ui.countries.CountriesScreenRoute
import com.example.mvvmarchitecture.ui.home.HomeScreenRoute
import com.example.mvvmarchitecture.ui.language.LanguagesScreenRoute
import com.example.mvvmarchitecture.ui.newssource.SourceScreenRoute
import com.example.mvvmarchitecture.ui.offline.OfflineArticleRoute
import com.example.mvvmarchitecture.ui.pagination.PaginationTopHeadlineRoute
import com.example.mvvmarchitecture.ui.search.SearchScreenRoute
import com.example.mvvmarchitecture.ui.topheadline.TopHeadLineScreenRoute
import com.example.mvvmarchitecture.utils.AppConstant

sealed class Route(val name: String) {
    object Home : Route("Home")
    object topHeadlineRoute : Route("topheadline?newsType={newsType}&newsTypeId={newsTypeId}") {
        fun buildPath(
            newsType: String = "",
            newsTypeId: String = "",
        ): String {
            return "topheadline?newsType=$newsType&newsTypeId=$newsTypeId"
        }
    }

    object NewsBySource : Route("newsbysource")
    object NewsByCountries : Route("newsbycountries")
    object NewsByLanguages : Route("newsbylangauages")
    object NewsBySearch : Route("newsbysearch")
    object OfflineArticle : Route("offlinearticle")
    object PaginationTopHeadline : Route("paginationtopheadline")

}

@Preview
@Composable
fun NewsNavHost() {

    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(navController = navController, startDestination = Route.Home.name) {

        composable(route = Route.Home.name) {
            HomeScreenRoute(navController)
        }
        composable(
            route = Route.topHeadlineRoute.name,
            arguments = listOf(
                navArgument(AppConstant.NewsBy.IntentParam.Key.NEWS_TYPE) {
                    type = NavType.StringType
                    defaultValue = ""
                },
                navArgument(AppConstant.NewsBy.IntentParam.Key.NEWS_TYPE_ID) {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) {
            val newsType = it.arguments?.getString(AppConstant.NewsBy.IntentParam.Key.NEWS_TYPE)
            val newsTypeId =
                it.arguments?.getString(AppConstant.NewsBy.IntentParam.Key.NEWS_TYPE_ID)
            TopHeadLineScreenRoute(
                onNewsClick = {
                    openCustomChromeTab(context = context, it)
                },
                newsType = newsType ?: "",
                newsIdentifier = newsTypeId ?: "",
                navController = navController
            )

        }

        composable(route = Route.NewsBySource.name) {
            SourceScreenRoute(onNewsClick = {
                navController.navigate(
                    route = Route.topHeadlineRoute.buildPath(
                        newsType = AppConstant.NewsBy.IntentParam.Value.SOURCE,
                        newsTypeId = it
                    )
                )
            }, navController = navController)
        }

        composable(route = Route.NewsByLanguages.name) {
            LanguagesScreenRoute(onNewsClick = {
                navController.navigate(
                    route = Route.topHeadlineRoute.buildPath(
                        newsType = AppConstant.NewsBy.IntentParam.Value.LANGUAGE,
                        newsTypeId = it
                    )
                )
            }, navController = navController)
        }

        composable(route = Route.NewsByCountries.name) {
            CountriesScreenRoute(onNewsClick = {
                navController.navigate(
                    route = Route.topHeadlineRoute.buildPath(
                        newsType = AppConstant.NewsBy.IntentParam.Value.COUNTRY,
                        newsTypeId = it
                    )
                )
            }, navController = navController)
        }

        composable(route = Route.NewsBySearch.name) {
            SearchScreenRoute(onNewsClick = {
                openCustomChromeTab(context = context, it)
            }, navController = navController)
        }

        composable(route = Route.OfflineArticle.name) {
            OfflineArticleRoute(onNewsClick = {
                openCustomChromeTab(context = context, it)
            }, navController = navController)
        }

        composable(route = Route.PaginationTopHeadline.name) {
            PaginationTopHeadlineRoute(onNewsClick = {
                openCustomChromeTab(context = context, it)
            }, navController = navController)
        }
    }
}


fun openCustomChromeTab(context: Context, url: String) {
    val builder = CustomTabsIntent.Builder()
    val customTabsIntent = builder.build()
    customTabsIntent.launchUrl(context, Uri.parse(url))
}
