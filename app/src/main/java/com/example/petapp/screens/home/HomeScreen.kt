package com.example.petapp.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.petapp.navigation.AllScreens
import com.example.petapp.sharedpreference.SharedPreference
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(navController: NavController) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val sharedPreference = SharedPreference(context)
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Home",
            color = Color.White,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.clickable {
                scope.launch {
                    sharedPreference.removeUser()
                    navController.navigate(route = AllScreens.LoginScreen.name){
                        navController.popBackStack()
                        navController.popBackStack()
                        navController.popBackStack()
                    }
                }
            })
    }
}