package com.srinand.agrocare.ui.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.NotificationsNone
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.srinand.agrocare.data.room.Alerts
import com.srinand.agrocare.data.room.PlantEvent
import com.srinand.agrocare.data.room.PlantState
import com.srinand.agrocare.ui.theme.FadedBorder
import com.srinand.agrocare.ui.theme.Primary
import com.srinand.agrocare.ui.theme.PrimaryVariant
import com.srinand.agrocare.viewmodels.MQTTClientViewModel

@Composable
fun NotificationsPage() {
    val viewModel = hiltViewModel<MQTTClientViewModel>()
    viewModel.SubscribeToAlert()
    val alertState by viewModel.alertState.collectAsState()
    Column(
        modifier = Modifier
            .background(Primary)
            .fillMaxSize(1F)
            .padding(horizontal = 24.dp, vertical = 32.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Rounded.NotificationsNone,
                contentDescription = "Alert",
                tint = FadedBorder,
                modifier = Modifier.size(36.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "Notifications",
                color = FadedBorder,
                style = MaterialTheme.typography.headlineLarge
            )
        }
        for(i in alertState.alerts){
            ListItem(alert = i)
        }
    }
}

@Composable
fun ListItem(alert : Alerts) {
    Column(
        modifier = Modifier
            .padding(bottom = 12.dp)
            .clip(RoundedCornerShape(12.dp))
            .fillMaxWidth(1F)
            .background(PrimaryVariant)
            .padding(vertical = 16.dp, horizontal = 12.dp)
    ) {
        Text(
            text = alert.message,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Black
        )
        Text(
            text = alert.time,
            style = MaterialTheme.typography.bodySmall,
            color = FadedBorder
        )
    }
}