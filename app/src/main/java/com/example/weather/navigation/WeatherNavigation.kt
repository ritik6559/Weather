package com.example.weather.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weather.screens.main.MainScreen
import com.example.weather.screens.main.MainViewModel
import com.example.weather.screens.splash.WeatherSplashScreen


@Composable
fun WeatherNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController,
        startDestination = WeatherScreens.SplashScreen.name) {

        composable(WeatherScreens.SplashScreen.name){
            WeatherSplashScreen(navController = navController)
        }

        composable(WeatherScreens.MainScreen.name){
            //passing viewmodel              //type of viewmodel.
            val mainViewModel = hiltViewModel<MainViewModel>()
            MainScreen(navController = navController,mainViewModel)
        }


    }
}