package com.example.petapp.screens.forgetPassword

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.citymall.screens.forgetPassword.ForgetPasswordViewModel
import com.example.petapp.components.CircleInductor
import com.example.petapp.components.LoginButton
import com.example.petapp.components.TextInput
import com.example.petapp.data.WrapperClass
import com.example.petapp.model.authentication.Authentication
import com.example.petapp.navigation.AllScreens
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ForgetPasswordScreen(
    navController: NavController,
    forgetPasswordViewModel: ForgetPasswordViewModel
) {
    var loading by remember {
        mutableStateOf(false)
    }
    val email = remember {
        mutableStateOf("")
    }
    val emailError = remember {
        mutableStateOf(false)
    }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    Surface(modifier = Modifier.fillMaxSize()) {
        if (!loading) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Forget Password", style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                    )
                )
                Spacer(modifier = Modifier.height(25.dp))
                TextInput(input = email, label = "Email", onAction = KeyboardActions {
                    if (!emailError.value) {
                        loading = true
                        scope.launch {
                            val response: WrapperClass<Authentication, Boolean, Exception> =
                                forgetPasswordViewModel.forgetPassword(
                                    email = email.value,
                                )
                            if (response.data?.status == "success") {
                                loading = false
                                navController.navigate(route = AllScreens.ResetPasswordScreen.name)
                            } else if (response.e != null) {
                                loading = false
                                Toast.makeText(
                                    context,
                                    "خطأ في الانترنت",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else if (response.data?.status == "fail" || response.data?.status == "error") {
                                loading = false
                                Toast.makeText(
                                    context,
                                    response.data?.message,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            keyboardController?.hide()
                        }
                    }
                }, error = emailError)
                Spacer(modifier = Modifier.height(15.dp))
                LoginButton(label = "Search") {
                    if (!emailError.value) {
                        loading = true
                        scope.launch {
                            val response: WrapperClass<Authentication, Boolean, Exception> =
                                forgetPasswordViewModel.forgetPassword(
                                    email = email.value,
                                )
                            if (response.data?.status == "success") {
                                loading = false
                                Toast.makeText(
                                    context,
                                    response.data?.message,
                                    Toast.LENGTH_SHORT
                                ).show()
                                 navController.navigate(route = AllScreens.ResetPasswordScreen.name)
                            }
                            else if (response.e != null) {
                                loading = false
                                Toast.makeText(
                                    context,
                                    "خطأ في الانترنت",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            else if (response.data?.status == "fail" || response.data?.status == "error") {
                                loading = false
                                Toast.makeText(
                                    context,
                                    response.data?.message,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }

            }
        } else {
            CircleInductor()
        }
    }
}