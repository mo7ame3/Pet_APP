package com.example.petapp.screens.pets

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import com.example.petapp.components.HomeCard
import com.example.petapp.constant.Constant
import com.example.petapp.data.WrapperClass
import com.example.petapp.model.home.Data
import com.example.petapp.model.home.Home
import com.example.petapp.model.pets.GetAllPets
import com.example.petapp.navigation.AllScreens
import com.example.petapp.screens.SharedViewModel
import com.example.petapp.ui.theme.MainColor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition",
    "StateFlowValueCalledInComposition"
)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetsScreen(navController: NavController , petsViewModel: PetsViewModel ,sharedViewModel: SharedViewModel) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var loading by remember {
        mutableStateOf(true)
    }
    var exception by remember {
        mutableStateOf(false)
    }
    val allPetsList = MutableStateFlow<List<Data>>(emptyList())

    val produce =
        produceState<WrapperClass<GetAllPets, Boolean, Exception>>(initialValue = WrapperClass()) {
            value = petsViewModel.getAllPets()
        }.value

    if (produce.data?.status == "successful") {
        scope.launch {
            allPetsList.emit(produce.data!!.data!!.rows)
            loading = false
        }
    }
    else if (produce.data?.status == "fail" || produce.data?.status == "error" || produce.e != null) {
        exception = true
        Toast.makeText(
            context,
            "خطأ في الانترنت",
            Toast.LENGTH_SHORT
        ).show()
    }
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(imageVector = Icons.Default.KeyboardArrowLeft, contentDescription = null)
                }
                Text(text = "Pets", style = MaterialTheme.typography.bodyMedium)
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = null,
                    tint = MainColor
                )
            }
        }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(25.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(top = 45.dp)
            ) {
                if (!loading && !exception) {
                    if (allPetsList.value.isNotEmpty()) {
                        HomeCard(item = allPetsList){ item ->
                            sharedViewModel.addPetDetails(item = item)
                            navController.navigate(route = AllScreens.DetailsScreen.name)
                        }
                    } else {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Nothing here \nYou don't have any pets.",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
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
                                val myPestData: WrapperClass<Home, Boolean, Exception> =
                                    petsViewModel.getMyPets(authorization = Constant.token)
                                if (myPestData.data?.status == "success") {
                                    if (myPestData.data != null) {
                                        scope.launch {
                                            allPetsList.emit(myPestData.data!!.data!!)
                                            loading = false
                                            exception = false
                                        }
                                    }
                                } else if (myPestData.data?.status == "fail" || myPestData.e != null) {
                                    exception = true
                                    Toast.makeText(
                                        context,
                                        "خطأ في الانترنت",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                            }
                        }) {
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
    }

}