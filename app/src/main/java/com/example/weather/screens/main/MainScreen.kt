package com.example.weather.screens.main

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.room.ktx.R
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.weather.data.DataOrException
import com.example.weather.model.Weather
import com.example.weather.model.WeatherItem
import com.example.weather.util.formatDate
import com.example.weather.util.formatDateTime
import com.example.weather.util.formatDecimals
import com.example.weather.widgets.WeatherAppBar

@Composable
fun MainScreen(navController: NavController,
               mainViewModel: MainViewModel = hiltViewModel()){


    //because we have a mutable state of DataOrException thus we need to produce state.
    val weatherData = produceState<DataOrException<Weather,Boolean,Exception>>(initialValue = DataOrException(loading = true)) {
        value = mainViewModel.getWeatherData(city = "Seattle")
    }.value


    if (weatherData.loading == true){
        CircularProgressIndicator()
    }else if (weatherData.data != null){

        //!!:- is called null check
        MainScaffold(weather = weatherData.data!!,navController)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(weather: Weather,navController: NavController) {

    Scaffold(
        topBar = {
            TopAppBar(title = { WeatherAppBar(title = weather.city.name + " ,${weather.city.country}",
                navController = navController,
                elevation = 5.dp){
                Log.d("TAG", "MainScaffold: Button Clicked")
            } })
        }
    ) {
        MainContent(data = weather,
            modifier = Modifier.padding(it))
    }
}

@Composable
fun MainContent(data: Weather,modifier: Modifier) {

    //every image is associated to a code thus we are getting the code of the image.
    val weatherItem = data.list[0]
    val imageUrl = "https://openweathermap.org/img/wn/${weatherItem.weather[0].icon}.png"


    Column(modifier
        .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = formatDate(weatherItem.dt),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(6.dp))

        Surface(modifier = Modifier
            .padding(4.dp)
            .size(200.dp),
            shape = CircleShape,
            color = Color(0xFFFFC400)

        ) {
            Column(verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                
                WeatherStateImage(imageUrl = imageUrl)
                Text(text = formatDecimals(weatherItem.temp.day) + "°",//converting float to integer.
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold)

                Text(text = weatherItem.weather[0].main,
                    fontStyle = FontStyle.Italic)

            }
        }

        HumidityWindPressure(weather = data.list[0])
        HorizontalDivider()
        SunsetSunriseRow(weather = data.list[0])
        HorizontalDivider()
        Surface(modifier = Modifier.padding()) {
            Text(text = "This Week",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold)

        }


        Surface(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
            color = Color(0xFFEEF1EF),
            shape = RoundedCornerShape(14.dp)
        ) {
            LazyColumn(modifier = Modifier.padding(2.dp),
                contentPadding = PaddingValues(1.dp)) {
                items(items = data.list){item: WeatherItem->
                    WeatherDetailRow(weather = item)
                }
            }
        }
    }
}

@Composable
fun WeatherDetailRow(weather: WeatherItem) {
    val imageUrl = "https://openweathermap.org/img/wn/${weather.weather[0].icon}.png"
    Surface(modifier = Modifier
        .padding(3.dp)
        .fillMaxWidth(),
        //.copy() allows us to pass diff vales for diff corners.
        //shape = CircleShape.copy(topEnd = CornerSize(6.dp))
        shape = RoundedCornerShape(30.dp)
    ) {
        Row(modifier = Modifier
            .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {
            //we can split Mon,Nov 29 at comma to get Mon and Mon wil be at index 0 and nov 20 wil be at 1 index
            Text(text = formatDate(weather.dt)
                .split(",")[0],
                modifier = Modifier.padding(start = 5.dp))

            WeatherStateImage(imageUrl = imageUrl)

            Surface(modifier = Modifier.padding(0.dp),
                shape = CircleShape,
                color = Color(0xFFFFC400)) {
                Text(text = weather.weather[0].description,
                    modifier = Modifier.padding(4.dp),
                    style = MaterialTheme.typography.bodySmall)
            }

            Text(text = buildAnnotatedString {
                withStyle(style = SpanStyle(
                    color = Color.Blue.copy(alpha = 0.7f),
                    fontWeight = FontWeight.SemiBold
                )){
                    append(formatDecimals(weather.temp.max) + "°")
                }
                withStyle(
                    SpanStyle(
                    color = Color.LightGray
                )
                ){
                    append(formatDecimals( weather.temp.min) + "°")
                }
            })

        }

    }
}

@Composable
fun SunsetSunriseRow(weather: WeatherItem) {
    Row(modifier = Modifier
        .padding(
            top = 8.dp,
            bottom = 8.dp
        )
        .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween) {
        Row {
            Image(painter = painterResource(id =com.example.weather.R.drawable.sunrise), contentDescription = "Sunrise Icon",
                modifier = Modifier.size(30.dp))
            Text(text = formatDateTime(weather.sunrise))
        }

        Row {
            Text(text = formatDateTime(weather.sunset))
            Image(painter = painterResource(id =com.example.weather.R.drawable.sunset), contentDescription = "Sunrise Icon",
                modifier = Modifier.size(30.dp))
        }


    }

}

@Composable
fun HumidityWindPressure(weather: WeatherItem) {
    Row(modifier = Modifier
        .padding(12.dp)
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween) {
        Row (modifier = Modifier.padding(4.dp)){
            Icon(painter = painterResource(id = com.example.weather.R.drawable.humidity),
                contentDescription = "Pressure Icon",
                modifier = Modifier.size(20.dp))
            Text(text = "${weather.humidity}%",
                style = MaterialTheme.typography.bodySmall
            )
        }

        Row (modifier = Modifier.padding(4.dp)){
            Icon(painter = painterResource(id = com.example.weather.R.drawable.pressure),
                contentDescription = "Pressure Icon",
                modifier = Modifier.size(20.dp))
            Text(text = "${weather.pressure}psi",
                style = MaterialTheme.typography.bodySmall
            )
        }

        Row (modifier = Modifier.padding(4.dp)){
            Icon(painter = painterResource(id = com.example.weather.R.drawable.wind),
                contentDescription = "Wind Icon",
                modifier = Modifier.size(20.dp))
            Text(text = "${weather.speed}mph",
                style = MaterialTheme.typography.bodySmall
            )
        }


    }


}

@Composable
fun WeatherStateImage(imageUrl: String) {
    //because we are getting image from an url thus we need to use painter
    AsyncImage(model = ImageRequest.Builder(LocalContext.current)
        .data(imageUrl)
        .crossfade(true)
        .build(),
        contentDescription ="icon",
        contentScale = ContentScale.Fit,
        modifier = Modifier.size(100.dp))

}
