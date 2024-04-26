package com.srinand.agrocare.ui.pages

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.RemoveRedEye
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.srinand.agrocare.R
import com.srinand.agrocare.ui.pages.navigation.LocalNavigator
import com.srinand.agrocare.ui.pages.navigation.Routes
import com.srinand.agrocare.ui.theme.Background
import com.srinand.agrocare.ui.theme.ButtonColor
import com.srinand.agrocare.ui.theme.OnPrimary
import com.srinand.agrocare.ui.theme.OnSecondary
import com.srinand.agrocare.ui.theme.Primary
import com.srinand.agrocare.ui.theme.Secondary
import com.srinand.agrocare.ui.theme.SecondaryVariant
import com.srinand.agrocare.viewmodels.MQTTClientViewModel
import kotlinx.coroutines.launch

@Composable
fun LoginPage() {
    val viewModel = hiltViewModel<MQTTClientViewModel>()
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val navHostController = LocalNavigator.current
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier.fillMaxSize(1F).background(Primary)
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth(1F)
                .align(Alignment.TopCenter),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.fillMaxSize(0.17F))
            Image(
                painter = painterResource(id = R.drawable.handcrop),
                contentDescription = "Image"
            )
            Spacer(modifier = Modifier.height(48.dp))
            Text(
                text = "Login",
                style = MaterialTheme.typography.titleMedium,
                color = OnPrimary
            )
            Spacer(modifier = Modifier.height(18.dp))
            OutlinedTextField(
                modifier = Modifier
                    .padding(horizontal = 24.dp, vertical = 16.dp)
                    .fillMaxWidth(1F)
                    .imePadding(),
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Secondary,
                    unfocusedContainerColor = Background,
                    focusedContainerColor = Background,
                    focusedIndicatorColor = Secondary,
                    unfocusedLabelColor = SecondaryVariant,
                    unfocusedIndicatorColor = SecondaryVariant,
                    unfocusedTextColor = SecondaryVariant,
                    unfocusedPlaceholderColor = SecondaryVariant,
                    unfocusedTrailingIconColor = SecondaryVariant,
                    focusedLabelColor = Secondary,
                    focusedPlaceholderColor = Secondary,
                    focusedTrailingIconColor = Secondary
                )
            )
            OutlinedTextField(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .fillMaxWidth(1F)
                    .imePadding(),
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Secondary,
                    unfocusedContainerColor = Background,
                    focusedContainerColor = Background,
                    focusedIndicatorColor = Secondary,
                    unfocusedLabelColor = SecondaryVariant,
                    unfocusedIndicatorColor = SecondaryVariant,
                    unfocusedTextColor = SecondaryVariant,
                    unfocusedPlaceholderColor = SecondaryVariant,
                    unfocusedTrailingIconColor = SecondaryVariant,
                    focusedLabelColor = Secondary,
                    focusedPlaceholderColor = Secondary,
                    focusedTrailingIconColor = Secondary
                ),
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                        Icon(imageVector = Icons.Outlined.RemoveRedEye, contentDescription = "eye")
                    }
                }
            )
        }
        Box(modifier = Modifier
            .padding(vertical = 64.dp, horizontal = 24.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(ButtonColor)
            .fillMaxWidth(1F)
            .align(Alignment.BottomCenter)
            .clickable {
                var res = false
                if (username.isNotEmpty() && password.isNotEmpty()) {
                    coroutineScope.launch {
                        res = viewModel.Connect(username, password)
                        if (res) {
                            Toast
                                .makeText(context, "Connected successfully", Toast.LENGTH_SHORT)
                                .show()
                            navHostController.navigate(Routes.MainPage.route)
                        } else {
                            Toast
                                .makeText(context, "Connection failed", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }else {
                    Toast
                        .makeText(context, "Username and Password can't be empty.", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        ){
            Text(
                text = "Login",
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(18.dp),
                style = MaterialTheme.typography.bodyLarge,
                color = OnPrimary,
                fontWeight = FontWeight.SemiBold
            )
        }
    }

}