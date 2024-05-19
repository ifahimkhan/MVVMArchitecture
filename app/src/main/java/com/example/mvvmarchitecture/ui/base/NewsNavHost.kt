package com.example.mvvmarchitecture.ui.base

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mvvmarchitecture.ui.home.HomeScreenRoute

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
    }
}