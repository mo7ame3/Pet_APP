package com.example.petapp.screens.details

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.petapp.components.GetSmallPhoto
import com.example.petapp.components.LoginButton
import com.example.petapp.model.home.Data
import com.example.petapp.screens.SharedViewModel
import com.example.petapp.screens.pets.PetsViewModel
import com.example.petapp.ui.theme.MainColor


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailsScreen(navController: NavController, petsViewModel: PetsViewModel, sharedViewModel: SharedViewModel) {



    Surface(modifier = Modifier.fillMaxSize()) {
    PetDetails(item = sharedViewModel.petDetails!!)
    }

}


@Composable
fun PetDetails(item: Data) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(25.dp), verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Image(
                painter = rememberAsyncImagePainter(model = item.image_url),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = item.name, style = MaterialTheme.typography.bodyLarge)
                Text(text = "${item.price} EGP", style = MaterialTheme.typography.bodyLarge)
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row {
                Icon(imageVector = Icons.Default.LocationOn, contentDescription = null)
                Spacer(modifier = Modifier.width(5.dp))
              Text(text = if (item.city != null) item.city else "No Location")
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Description", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = item.description, style = MaterialTheme.typography.bodyMedium)
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (item.user_image == "image_url" || item.user_image == "https" || item.user_image == null) {
                        GetSmallPhoto()
                    } else {
                        GetSmallPhoto(uri = item.image_url)
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = item.user_name, style = MaterialTheme.typography.bodyLarge)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Surface(
                        modifier = Modifier.size(25.dp),
                        shape = RoundedCornerShape(5.dp),
                        color = MainColor
                    ) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowRight,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                    Text(
                        text = "See Profile",
                        style = MaterialTheme.typography.bodyMedium,
                        textDecoration = TextDecoration.Underline
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            LoginButton(label = "Chat", modifier = Modifier.fillMaxWidth(0.5f)) {

            }
        }
    }
}
