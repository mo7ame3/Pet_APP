package com.example.petapp.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.citymall.screens.forgetPassword.ForgetPasswordViewModel
import com.example.petapp.screens.SharedViewModel
import com.example.petapp.screens.details.DetailsScreen
import com.example.petapp.screens.forgetPassword.ForgetPasswordScreen
import com.example.petapp.screens.forgetPassword.ResetPasswordScreen
import com.example.petapp.screens.home.HomeScreen
import com.example.petapp.screens.home.HomeViewModel
import com.example.petapp.screens.login.LoginScreen
import com.example.petapp.screens.login.LoginViewModel
import com.example.petapp.screens.pets.PetsScreen
import com.example.petapp.screens.pets.PetsViewModel
import com.example.petapp.screens.pets.myPets.MyPetsScreen
import com.example.petapp.screens.profile.ProfileScreen
import com.example.petapp.screens.profile.ProfileViewModel
import com.example.petapp.screens.register.RegisterScreen
import com.example.petapp.screens.register.RegisterViewModel
import com.example.petapp.screens.splash.SplashScreen

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun NavGraph() {
    val sharedViewModel: SharedViewModel = viewModel()
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AllScreens.SplashScreen.name
    ) {

        composable(route = AllScreens.SplashScreen.name) {
            SplashScreen(navController = navController)
        }

        composable(route = AllScreens.LoginScreen.name) {
            val loginViewModel = hiltViewModel<LoginViewModel>()
            LoginScreen(navController = navController, loginViewModel = loginViewModel)
        }

        composable(route = AllScreens.RegisterScreen.name) {
            val registerViewModel = hiltViewModel<RegisterViewModel>()
            RegisterScreen(navController = navController, registerViewModel = registerViewModel)
        }

        composable(route = AllScreens.ForgetPasswordScreen.name) {
            val forgetPasswordViewModel = hiltViewModel<ForgetPasswordViewModel>()
            ForgetPasswordScreen(
                navController = navController,
                forgetPasswordViewModel = forgetPasswordViewModel
            )
        }
        composable(route = AllScreens.ResetPasswordScreen.name) {
            val forgetPasswordViewModel = hiltViewModel<ForgetPasswordViewModel>()
            ResetPasswordScreen(
                navController = navController,
                forgetPasswordViewModel = forgetPasswordViewModel
            )
        }

        composable(route = AllScreens.HomeScreen.name) {
            val homeViewModel = hiltViewModel<HomeViewModel>()
            HomeScreen(
                navController = navController,
                homeViewModel = homeViewModel,
                sharedViewModel = sharedViewModel
            )
        }
        composable(route = AllScreens.ProfileScreen.name + "/{userId}", arguments = listOf(
            navArgument(name = "userId") {
                type = NavType.StringType
            }
        )) { data ->
            val profileViewModel = hiltViewModel<ProfileViewModel>()
            ProfileScreen(
                navController = navController,
                userId = data.arguments!!.getString("userId").toString(),
                profileViewModel = profileViewModel
            )
        }

        composable(route = AllScreens.MyPetsScreen.name) {
            val petsViewModel = hiltViewModel<PetsViewModel>()
            MyPetsScreen(
                navController = navController,
                petsViewModel = petsViewModel,
                sharedViewModel = sharedViewModel
            )
        }
        composable(route = AllScreens.AllPetsScreen.name) {
            val petsViewModel = hiltViewModel<PetsViewModel>()
            PetsScreen(
                navController = navController,
                petsViewModel = petsViewModel,
                sharedViewModel = sharedViewModel
            )
        }

        composable(route = AllScreens.DetailsScreen.name) {

            val petsViewModel = hiltViewModel<PetsViewModel>()
            DetailsScreen(
                navController = navController,
                petsViewModel = petsViewModel,
                sharedViewModel = sharedViewModel
            )
        }

    }
}