package com.example.weather.network

import com.example.weather.model.Weather
import com.example.weather.model.WeatherObject
import com.example.weather.util.Constants
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface WeatherApi {
    @GET(value = "data/2.5/forecast/daily")//passing path before ?.
    suspend fun getWeather(
        //q comes after ? in the link.
        @Query("q") query: String,//city eg in link q = portland.
        //at the end of the link.
        @Query("units") units: String = "imperial",//unit of temp.
        //passing appid
        @Query("appid") appid: String = Constants.API_KEY
    ): Weather
}