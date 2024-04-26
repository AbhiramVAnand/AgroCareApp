package com.srinand.agrocare.ui.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.srinand.agrocare.ui.pages.navigation.BottomBarScreen
import com.srinand.agrocare.ui.pages.navigation.LocalNavigator
import com.srinand.agrocare.ui.pages.navigation.Routes
import com.srinand.agrocare.ui.theme.Background
import com.srinand.agrocare.ui.theme.ButtonColor
import com.srinand.agrocare.ui.theme.FadedBorder
import com.srinand.agrocare.ui.theme.Primary
import com.srinand.agrocare.ui.theme.PrimaryVariant
import com.srinand.agrocare.ui.theme.Secondary
import com.srinand.agrocare.viewmodels.MQTTClientViewModel

@Composable
fun LogoutPage() {
    val navHostController = LocalNavigator.current
    val viewModel = hiltViewModel<MQTTClientViewModel>()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .background(Primary)
            .fillMaxWidth(1F)
            .fillMaxHeight(1F)
            .padding(32.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(Primary)
                .padding(32.dp)
        ) {
            Text(
                text = "Logout from AgroCare?",
                color = FadedBorder,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(64.dp))
            Row(
                modifier = Modifier.fillMaxWidth(1F),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = {
                        navHostController.popBackStack(route = Routes.MainPage.route, inclusive = true)
                        navHostController.navigate(Routes.MainPage.route)
                    },
                    modifier = Modifier,
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryVariant, contentColor = Secondary)
                ) {
                    Text(
                        text = "Cancel",
                        modifier = Modifier
                            .padding(vertical = 8.dp),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Spacer(modifier= Modifier.width(24.dp))
                Button(
                    onClick = {
                        viewModel.Logout()
                        navHostController.popBackStack(route = BottomBarScreen.Home.route, inclusive = true)
                        navHostController.navigate(Routes.StartUpPage.route)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = ButtonColor, contentColor = Secondary)
                ) {
                    Text(
                        text = "Logout",
                        modifier = Modifier
                            .padding(vertical = 8.dp),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}