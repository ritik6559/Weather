package com.example.weather.di

import com.example.weather.network.WeatherApi
import com.example.weather.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Singleton
    @Provides
    fun provideOpenWeatherApi(): WeatherApi{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())//converting json file into kotlin objects.
            .build()
            .create(WeatherApi::class.java)//in order to create we need to pass module thai is weather api.
    }

}