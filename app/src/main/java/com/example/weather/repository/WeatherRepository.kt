package com.example.weather.repository

import com.example.weather.data.DataOrException
import com.example.weather.model.Weather
import com.example.weather.network.WeatherApi
import javax.inject.Inject

//to get data from weather api. @Inject is used to inject dependency.
class WeatherRepository @Inject constructor(private val api: WeatherApi){

    suspend fun getWeather(cityQuery: String)//same name as in weather api.
    : DataOrException<Weather,Boolean,Exception>{
        val response = try{
            api.getWeather(query = cityQuery)
        }catch(e: Exception){
            return DataOrException(e = e)
        }
        return DataOrException(data = response)
    }
}