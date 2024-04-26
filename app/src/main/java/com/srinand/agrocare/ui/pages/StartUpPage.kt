package com.srinand.agrocare.ui.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.srinand.agrocare.R
import com.srinand.agrocare.ui.pages.navigation.LocalNavigator
import com.srinand.agrocare.ui.pages.navigation.Routes
import com.srinand.agrocare.ui.theme.Background
import com.srinand.agrocare.ui.theme.ButtonColor
import com.srinand.agrocare.ui.theme.OnBackground
import com.srinand.agrocare.ui.theme.OnPrimary
import com.srinand.agrocare.ui.theme.OnSecondary

@Composable
fun StartUpPage() {
    val navHostController = LocalNavigator.current
    Box(modifier = Modifier
        .background(Background)
        .fillMaxSize(1F)
    ){
        Column(
            modifier = Modifier.fillMaxWidth(1F).align(Alignment.TopCenter),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.fillMaxHeight(0.18F))
            Text(
                text = "Welcome to",
                style = MaterialTheme.typography.headlineLarge,
                color = OnBackground

            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "AgroCare",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.ExtraBold,
                color = OnBackground
            )
            Spacer(modifier = Modifier.fillMaxHeight(0.2F))
            Image(
                painter = painterResource(id = R.drawable.plantmonitor),
                contentDescription = "Image",
                modifier = Modifier.fillMaxWidth(0.8F)
            )
            Spacer(modifier = Modifier.fillMaxHeight(0.18F))
            Text(
                text = "Your precision agriculture partner",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.ExtraBold,
                color = OnBackground
            )
        }
        Box(modifier = Modifier
            .padding(vertical = 64.dp, horizontal = 24.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(ButtonColor)
            .fillMaxWidth(1F)
            .align(Alignment.BottomCenter)
            .clickable {
                navHostController.navigate(Routes.LoginPage.route)
            }
        ){
            Text(
                text = "Start Monitoring",
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