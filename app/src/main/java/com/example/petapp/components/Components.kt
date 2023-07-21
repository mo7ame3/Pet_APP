package com.example.petapp.components

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.petapp.R
import com.example.petapp.model.home.Data
import com.example.petapp.ui.theme.Gold
import com.example.petapp.ui.theme.GreyDark
import com.example.petapp.ui.theme.GreyLight
import com.example.petapp.ui.theme.MainColor
import com.example.petapp.ui.theme.MainLight1
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun CircleInductor() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(color = MainLight1)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextInput(
    modifier: Modifier = Modifier,
    input: MutableState<String>,
    keyboardType: KeyboardType = KeyboardType.Email,
    error: MutableState<Boolean>,
    label: String,
    onAction: KeyboardActions = KeyboardActions.Default,
    isSingleLine: Boolean = true,
) {

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 25.dp, end = 25.dp),
        shape = RoundedCornerShape(25.dp),
        label = { Text(text = label) },
        value = input.value,
        onValueChange = {
            if (label == "Email") {
                val emailRegex = "^[A-Za-z](.*)(@)(.+)(\\.)(.+)"
                input.value = it
                error.value = !emailRegex.toRegex().matches(it)
            } else if (label == "Phone") {
                val phoneRegex = "1[0-9](.+)"
                error.value = !phoneRegex.toRegex().matches(it)
                if (it.length <= 10) {
                    input.value = it
                }
            } else if (label == "Name") {
                val nameRegex = "^[a-zA-Zأ-ي]+(([',. -][a-zA-Zأ-ي])?[a-zA-Zأ-ي]*)*$"
                input.value = it
                error.value = !nameRegex.toRegex().matches(it)
            } else if (label == "City") {
                val cityRegex = "^([a-zA-Z\u0080-\u024F]+(?:. |-| |'))*[a-zA-Z\u0080-\u024F]*$"
                input.value = it
                error.value = !cityRegex.toRegex().matches(it)
            } else if (label == "Search") {
                val searchRegex = "^[a-zA-Zأ-ي]+(([',. -][a-zA-Zأ-ي])?[a-zA-Zأ-ي]*)*$"
                input.value = it
                error.value = !searchRegex.toRegex().matches(it)
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        keyboardActions = onAction,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            errorBorderColor = Color.Red,
            errorCursorColor = Color.Red,
            errorLabelColor = Color.Red,
        ),
        singleLine = isSingleLine,
        leadingIcon = {
            when (label) {
                "Email" -> {
                    Icon(imageVector = Icons.Default.Email, contentDescription = null)
                }

                "Phone" -> {
                    Text(text = "+20")
                }

                "Name" -> {
                    Icon(imageVector = Icons.Default.Person, contentDescription = null)
                }

                "Search" -> {
                    Icon(imageVector = Icons.Default.Search, contentDescription = null)
                }
            }
        },
        isError = error.value
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordInput(
    modifier: Modifier = Modifier,
    password: MutableState<String>,
    keyboardType: KeyboardType = KeyboardType.Password,
    isSingleLine: Boolean = true,
    eye: MutableState<Boolean>,
    onButtonAction: () -> Unit,
    onAction: KeyboardActions = KeyboardActions.Default,
    label: String = "Password",
) {
    val visualTransformation = if (eye.value) VisualTransformation.None
    else PasswordVisualTransformation()
    OutlinedTextField(singleLine = isSingleLine,
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 25.dp, end = 25.dp),
        shape = RoundedCornerShape(25.dp),
        label = { Text(text = label) },
        value = password.value,
        onValueChange = {
            if (it.isNotBlank()) {
                password.value = it
            } else {
                password.value = ""
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        keyboardActions = onAction,
        visualTransformation = visualTransformation,
        trailingIcon = {
            IconButton(onClick = { onButtonAction.invoke() }) {
                if (eye.value) Icon(
                    painter = painterResource(id = R.drawable.visibility),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                else Icon(
                    painter = painterResource(id = R.drawable.visibilityoff),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            }
        },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Lock, contentDescription = null)

        })
}

@Composable
fun LoginButton(label: String, onClick: () -> Unit) {
    Button(
        onClick = {
            onClick.invoke()
        },
        shape = CircleShape,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 25.dp, end = 25.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MainLight1
        )
    ) {
        Text(
            text = label, color = Color.White
        )
    }
}

@Composable
fun OtpTextField(
    modifier: Modifier = Modifier,
    otpText: String,
    otpCount: Int = 6,
    onOtpTextChange: (String, Boolean) -> Unit
) {
    LaunchedEffect(Unit) {
        if (otpText.length > otpCount) {
            throw IllegalArgumentException("Otp text value must not have more than otpCount: $otpCount characters")
        }
    }

    BasicTextField(modifier = modifier,
        value = TextFieldValue(otpText, selection = TextRange(otpText.length)),
        onValueChange = {
            if (it.text.length <= otpCount) {
                onOtpTextChange.invoke(it.text, it.text.length == otpCount)
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
        decorationBox = {
            Row(horizontalArrangement = Arrangement.Center) {
                repeat(otpCount) { index ->
                    CharView(
                        index = index, text = otpText
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        })
}

@Composable
private fun CharView(
    index: Int, text: String
) {
    val isFocused = text.length == index
    val char = when {
        index == text.length -> "0"
        index > text.length -> ""
        else -> text[index].toString()
    }
    Text(
        modifier = Modifier
            .width(40.dp)
            .height(30.dp)
            .border(
                1.dp, when {
                    isFocused -> GreyDark
                    else -> GreyLight
                }, RoundedCornerShape(8.dp)
            )
            .padding(2.dp),
        text = char,
        style = MaterialTheme.typography.bodyMedium,
        color = if (isFocused) {
            GreyLight
        } else {
            GreyDark
        },
        textAlign = TextAlign.Center
    )
}


@Composable
fun BottomBar(
    selected: MutableState<String>,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        shape = RoundedCornerShape(topEnd = 20.dp, topStart = 20.dp),
        color = MainColor
    ) {
        NavigationBar(containerColor = MainColor, contentColor = MainColor) {
            NavigationBarItem(selected = selected.value == "home", onClick = {
                selected.value = "home"
            }, icon = {
                Icon(
                    painter = painterResource(id = R.drawable.home),
                    contentDescription = null,
                    tint = GreyLight,
                    modifier = Modifier.size(30.dp)
                )
            }, colors = NavigationBarItemDefaults.colors(
                indicatorColor = GreyDark
            )
            )

            NavigationBarItem(selected = selected.value == "chat", onClick = {
                selected.value = "chat"

            }, icon = {
                Icon(
                    painter = painterResource(id = R.drawable.chat),
                    contentDescription = null,
                    tint = GreyLight,
                    modifier = Modifier.size(30.dp)
                )
            }, colors = NavigationBarItemDefaults.colors(
                indicatorColor = GreyDark
            )
            )

            NavigationBarItem(selected = selected.value == "add", onClick = {
                selected.value = "add"

            }, icon = {
                Card(
                    modifier = Modifier.size(50.dp),
                    shape = RoundedCornerShape(
                        topEnd = 15.dp,
                        topStart = 15.dp,
                        bottomEnd = 15.dp,
                        bottomStart = 15.dp
                    ),
                    colors = CardDefaults.cardColors(
                        containerColor = Gold
                    )
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                            tint = GreyLight,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
            }, colors = NavigationBarItemDefaults.colors(
                indicatorColor = GreyDark
            )
            )
            NavigationBarItem(selected = selected.value == "favorite", onClick = {
                selected.value = "favorite"

            }, icon = {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = null,
                    tint = GreyLight,
                    modifier = Modifier.size(30.dp)
                )
            }, colors = NavigationBarItemDefaults.colors(
                indicatorColor = GreyDark
            )
            )
            NavigationBarItem(selected = selected.value == "profile", onClick = {
                selected.value = "profile"

            }, icon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = GreyLight,
                    modifier = Modifier.size(30.dp)
                )
            }, colors = NavigationBarItemDefaults.colors(
                indicatorColor = GreyDark
            )
            )
        }
    }
}


@Composable
fun HomeRowCard(
    firTitle: String,
    secTitle: String,
    firIcon: Int,
    secIcon: Int,
    firPhoto: Int,
    secPhoto: Int
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Card(
            modifier = Modifier
                .weight(1f)
                .height(100.dp),
            border = BorderStroke(1.dp, color = MainColor)
        ) {
            HomeCardColumn(title = firTitle, icon = firIcon, photo = firPhoto)
        }
        Spacer(modifier = Modifier.width(5.dp))
        Card(
            modifier = Modifier
                .weight(1f)
                .height(100.dp),
            border = BorderStroke(1.dp, color = MainColor)
        ) {
            HomeCardColumn(title = secTitle, icon = secIcon, photo = secPhoto)
        }
    }
}

@Composable
fun HomeCardColumn(title: String, icon: Int, photo: Int) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(start = 5.dp, end = 5.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    modifier = Modifier.size(25.dp)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(text = title, style = MaterialTheme.typography.bodyMedium)
            }
            Image(
                painter = painterResource(id = photo),
                contentDescription = null,
                modifier = Modifier.size(75.dp)
            )
        }
    }
}


@Composable
fun HomeCardOnline(item: Data) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(5.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = item.image_url),
                contentDescription = null,
                modifier = Modifier.fillMaxHeight().fillMaxSize(.5f)
            )
        }
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = item.name)
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = item.price.toString() + "EGP")
        Row {
            Icon(imageVector = Icons.Default.LocationOn, contentDescription = null)
            Spacer(modifier = Modifier.width(5.dp))
            Text(text = if (item.city != null) item.city else "No Location")
        }
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun Home(item: MutableStateFlow<List<Data>>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Text(text = "What are you looking for ?", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(5.dp))
        HomeRowCard(
            firTitle = "Pets",
            secTitle = "Doctors",
            firIcon = R.drawable.petsicon,
            secIcon = R.drawable.doctorsicon,
            firPhoto = R.drawable.petsphoto,
            secPhoto = R.drawable.doctorsphoto
        )
        Spacer(modifier = Modifier.height(5.dp))
        HomeRowCard(
            firTitle = "Food",
            secTitle = "Accessories",
            firIcon = R.drawable.foodicon,
            secIcon = R.drawable.accessoriesicon,
            firPhoto = R.drawable.foodphoto,
            secPhoto = R.drawable.accesoriesphoto
        )
        Spacer(modifier = Modifier.height(5.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Newly Added", style = MaterialTheme.typography.bodyLarge)
            Text(
                text = "See Mord",
                style = MaterialTheme.typography.bodyLarge,
                color = Gold,
                textDecoration = TextDecoration.Underline
            )
        }
        Spacer(modifier = Modifier.height(5.dp))
        for (i in 0 until item.value.size step 2) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .height(200.dp),
                    border = BorderStroke(1.dp, color = MainColor)
                ) {
                    HomeCardOnline(item = item.value[i])
                }
                if (i + 2 < item.value.size) {
                    Spacer(modifier = Modifier.width(5.dp))
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .height(200.dp),
                        border = BorderStroke(1.dp, color = MainColor)
                    ) {
                        HomeCardOnline(item = item.value[i + 1])
                    }
                } else {
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .height(200.dp),
                        border = BorderStroke(0.dp, color = Color.White)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color = Color.White)
                                .padding(5.dp),
                        ) {}
                    }
                }
            }
            Spacer(modifier = Modifier.height(5.dp))
        }
    }
}