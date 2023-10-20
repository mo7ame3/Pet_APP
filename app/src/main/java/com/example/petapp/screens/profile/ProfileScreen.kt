package com.example.petapp.screens.profile

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.petapp.components.CircleInductor
import com.example.petapp.components.ProfileForm
import com.example.petapp.constant.Constant
import com.example.petapp.data.WrapperClass
import com.example.petapp.model.profile.Data
import com.example.petapp.model.profile.Profile
import com.example.petapp.sharedpreference.SharedPreference
import com.example.petapp.ui.theme.MainColor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition", "StateFlowValueCalledInComposition")
@Composable
fun ProfileScreen(
    userId: String,
    navController: NavController,
    profileViewModel: ProfileViewModel
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val loading = remember {
        mutableStateOf(true)
    }
    val delete = remember {
        mutableStateOf(false)
    }
    var exception by remember {
        mutableStateOf(false)
    }
    val profileData = MutableStateFlow<List<Data>>(emptyList())
    val sharedPreference = SharedPreference(context)
    val response = produceState<WrapperClass<Profile, Boolean, Exception>>(
        initialValue = WrapperClass(),
    ) {
        value = profileViewModel.profile(userId = userId, authorization = Constant.token)
    }.value

    if (response.data?.status == "successful") {
        scope.launch {
            loading.value = false
            profileData.emit(response.data!!.data!!)
        }
    } else if (response.data?.status == "fail" || response.data?.status == "error" || response.e != null) {
        exception = true
        Toast.makeText(
            context,
            "خطأ في الانترنت",
            Toast.LENGTH_SHORT
        ).show()
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        if (!loading.value && !exception) {
            ProfileForm(
                navController = navController,
                item = profileData.value[0],
                profileViewModel = profileViewModel,
                scope = scope,
                loading = loading,
                sharedPreference = sharedPreference,
                delete = delete
            )
        } else if (loading.value && !exception) {
            CircleInductor()
        } else if (exception) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(onClick = {
                    exception = false
                    loading.value = true
                    scope.launch {
                        val responseRef: WrapperClass<Profile, Boolean, Exception> =
                            profileViewModel.profile(
                                userId = userId,
                                authorization = Constant.token
                            )
                        if (responseRef.data?.status == "successful") {
                            if (responseRef.data != null) {
                                scope.launch {
                                    profileData.emit(responseRef.data!!.data!!)
                                    loading.value = false
                                    exception = false
                                }
                            }
                        } else if (responseRef.data?.status == "fail" || responseRef.e != null) {
                            exception = true
                            Toast.makeText(
                                context,
                                "خطأ في الانترنت",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                })
                {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = null,
                        tint = MainColor,
                        modifier = Modifier.size(60.dp)
                    )
                }
            }
        }
    }
}


