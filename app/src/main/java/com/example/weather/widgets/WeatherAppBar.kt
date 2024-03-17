package com.example.weather.widgets

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun WeatherAppBar(
    title: String = "title",
    icon: ImageVector? = null,
    isMainScreen: Boolean = true,//this is passed to check if we are on the main screen or not because the content of the app bar changes as screen changes.
    elevation: Dp = 0.dp,
    navController: NavController,
    onAddActionClicked: () -> Unit = {},
    onButtonClicked: () -> Unit = {},
){
    TopAppBar(
        title = { Text(text = title,
            style = TextStyle(fontWeight = FontWeight.Bold,
                fontSize = 15.sp)
        ) },
        actions = {
                  if (isMainScreen){
                      IconButton(onClick = { /*TODO*/ }) {//it makes an icon a button.
                          Icon(
                              imageVector = Icons.Default.Search,
                              contentDescription = "Search Icon"
                          )
                      }
                      IconButton(onClick = { /*TODO*/ }) {
                          Icon(
                              imageVector = Icons.Rounded.MoreVert,
                              contentDescription = "MoreVertical Icon"
                          )

                      }
                  }else Box{}
        },
        navigationIcon = {
                         if (icon != null){
                             Icon(imageVector = icon, contentDescription = "icon",
                                 tint = Color.Black,
                                 modifier = Modifier.clickable {
                                     onButtonClicked.invoke()
                                 })
                         }
        },
        colors = TopAppBarDefaults.topAppBarColors(Color.Transparent),
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = elevation
            )


    )

}