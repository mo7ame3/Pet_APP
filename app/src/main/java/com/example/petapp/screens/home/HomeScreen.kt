package com.example.petapp.screens.home

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.petapp.components.BottomBar
import com.example.petapp.components.CircleInductor
import com.example.petapp.components.Home
import com.example.petapp.components.TextInput
import com.example.petapp.data.WrapperClass
import com.example.petapp.model.home.Data
import com.example.petapp.model.home.Home
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@Composable
fun HomeScreen(navController: NavController, homeViewModel: HomeViewModel) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val keyboardController = LocalSoftwareKeyboardController.current
    val selectBottomBar = remember {
        mutableStateOf("home")
    }
    var loading by remember {
        mutableStateOf(true)
    }
    var exception by remember {
        mutableStateOf(false)
    }
    val homeList = MutableStateFlow<List<Data>>(emptyList())

    if (selectBottomBar.value == "home") {
        val produce =
            produceState<WrapperClass<Home, Boolean, Exception>>(initialValue = WrapperClass()) {
                value = homeViewModel.home()
            }.value

        if (produce.data?.status == "success") {
            scope.launch {
                homeList.emit(produce.data!!.data!!)
                loading = false
            }
        } else if (produce.data?.status == "fail" || produce.data?.status == "error" || produce.e != null) {
            exception = true
            Toast.makeText(
                context,
                "خطأ في الانترنت",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    // Swipe Refresh
    var swipeLoading by remember {
        mutableStateOf(false)
    }
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = swipeLoading)
    SwipeRefresh(state = swipeRefreshState, onRefresh = {
        if (selectBottomBar.value == "home") {
            swipeLoading = true
            loading = false
            scope.launch {
                val homeRefresh: WrapperClass<Home, Boolean, Exception> =
                    homeViewModel.home()
                swipeLoading = if (homeRefresh.data?.status == "success") {
                    homeList.emit(homeRefresh.data!!.data!!)
                    false
                } else {
                    false
                }
            }
        }
    })
    {
        Scaffold(
            topBar = {
                Row(modifier = Modifier.fillMaxWidth()) {
                    val search = remember {
                        mutableStateOf("")
                    }
                    val searchError = remember {
                        mutableStateOf(false)
                    }
                    TextInput(
                        input = search,
                        error = searchError,
                        label = "Search",
                        onAction = KeyboardActions {
                            keyboardController?.hide()
                        },
                        keyboardType = KeyboardType.Text
                    )
                }
            },
            bottomBar = {
                BottomBar(selected = selectBottomBar)
            })
        {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(25.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 45.dp)
                ) {
                    if (!loading && !exception) {
                        if (selectBottomBar.value == "home") {
                            Home(item = homeList)
                        }
                    } else if (loading && !exception) {
                        CircleInductor()
                    } else if (exception) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            IconButton(onClick = {
                                exception = false
                                loading = true
                                scope.launch {
                                    if (selectBottomBar.value == "home") {
                                        val homeData: WrapperClass<Home, Boolean, Exception> =
                                            homeViewModel.home()
                                        if (homeData.data?.status == "success") {
                                            if (homeData.data != null) {
                                                scope.launch {
                                                    homeList.emit(homeData.data!!.data!!)
                                                    loading = false
                                                    exception = false
                                                }
                                            }
                                        } else if (homeData.data?.status == "fail" || homeData.e != null) {
                                            exception = true
                                            Toast.makeText(
                                                context,
                                                "خطأ في الانترنت",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }
                                }
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Refresh, contentDescription = null,
                                    modifier = Modifier.size(60.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

    }
}






