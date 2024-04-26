package com.srinand.agrocare.ui.pages

import android.annotation.SuppressLint
import android.widget.Switch
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.Agriculture
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.srinand.agrocare.R
import com.srinand.agrocare.data.room.PlantEvent
import com.srinand.agrocare.data.room.PlantState
import com.srinand.agrocare.data.room.Plants
import com.srinand.agrocare.ui.theme.Background
import com.srinand.agrocare.ui.theme.FadedBorder
import com.srinand.agrocare.ui.theme.Secondary
import com.srinand.agrocare.ui.theme.SecondaryVariant
import com.srinand.agrocare.viewmodels.MQTTClientViewModel
import com.srinand.agrocare.viewmodels.RoomViewModel
import java.util.Calendar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomePage(
    state : PlantState,
    onEvent : (PlantEvent) -> Unit
) {
    val viewModel = hiltViewModel<MQTTClientViewModel>()
    val userName = viewModel.getUname().capitalize()
    val alertState by viewModel.alertState.collectAsState()
    val roomViewModel = hiltViewModel<RoomViewModel>()

    val time = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    val greeting = when(time){
        in 4..11 -> "Good morning,"
        in 12..15 -> "Good afternoon,"
        in 16..19 -> "Good evening,"
        else -> "Good night,"
    }
    val date = Calendar.getInstance().get(Calendar.DAY_OF_MONTH).toString()
    val month = when(Calendar.getInstance().get(Calendar.MONTH)){
        0 -> "Jan"
        1 -> "Feb"
        2 -> "Mar"
        3 -> "Apr"
        4 -> "May"
        5 -> "June"
        6 -> "July"
        7 -> "Aug"
        8 -> "Sept"
        9 -> "Oct"
        10 -> "Nov"
        11 -> "Dec"
        else -> {"Error"}
    }
    val day = when(Calendar.getInstance().get(Calendar.DAY_OF_WEEK)){
        1 -> "Sunday"
        2 -> "Monday"
        3 -> "Tuesday"
        4 -> "Wednesday"
        5 -> "Thursday"
        6 -> "Friday"
        7 -> "Saturday"
        else -> {"Error"}
    }

    if(state.isAddingPlant){
        AddPlants(state = state, onEvent = onEvent)
    }

    Column(modifier = Modifier
        .fillMaxWidth(1F)
        .fillMaxHeight(1F)
        .background(Background)
        .verticalScroll(rememberScrollState())
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth(1F)
                .padding(vertical = 32.dp, horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "AgroCare",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Black,
                color = Secondary,
                modifier = Modifier.weight(1F),
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth(1F)
                .padding(horizontal = 24.dp)
        ) {
            Text(
                text = greeting,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.ExtraBold,
                color = FadedBorder,
                modifier = Modifier
                    .padding(top = 12.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = userName,
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.width(8.dp))
                Image(
                    modifier = Modifier.size(32.dp),
                    painter = painterResource(id = R.drawable.hand_wave),
                    contentDescription = "HandWave"
                )
            }
            Spacer(modifier = Modifier.height(32.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth(1F)
                    .height(180.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.tempcardbg),
                    contentDescription = "BG Image",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.matchParentSize()
                )
                Column (
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(vertical = 12.dp, horizontal = 32.dp)
                        .align(Alignment.BottomStart)
                ){
                    Text(
                        text = "18\u2103",
                        color = FadedBorder,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = day + ", " + month +" "+date,
                        color = FadedBorder,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Rounded.Agriculture,
                    contentDescription = "AGriculture",
                    tint = FadedBorder,
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "My Garden",
                    color = FadedBorder,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.weight(1F)
                )
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add",
                    tint = FadedBorder,
                    modifier = Modifier
                        .size(28.dp)
                        .clickable {
                            onEvent(PlantEvent.ShowDialog)
                        }
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth(1F)
                    .padding(bottom = 82.dp)
            ) {
                state.plants.chunked(2).forEach {plants ->
                    PlantRow(plants)
                }
            }
        }
    }
}

@Composable
fun PlantRow(plants: List<Plants>) {
    val viewModel = hiltViewModel<MQTTClientViewModel>()
    val plant1= plants[0]
    val plant2 = if (plants.size==2) {
        plants[1]
    }else{
        null
    }
    var plant1motor by remember {
        mutableStateOf(false)
    }
    var plant2motor by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    Row(
        modifier = Modifier.fillMaxWidth(1F)
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .width(180.dp)
                .clip(RoundedCornerShape(12.dp))
                .border(
                    BorderStroke(width = 1.dp, color = SecondaryVariant), // Border customization
                    shape = RoundedCornerShape(12.dp) // Match corner shape of clip
                )
                .padding(14.dp)
        ) {
            Text(
                text = viewModel.SubscribeToPlant(plant1.name).collectAsState().value!!,
                modifier = Modifier.padding(bottom = 4.dp),
                color = Secondary,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = plant1.name,
                modifier = Modifier.padding(bottom = 4.dp),
                color = Secondary,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.SemiBold
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(1F)
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Motor",
                    modifier = Modifier.padding(bottom = 4.dp),
                    color = Secondary
                )
                Switch(
                    checked = plant1motor,
                    onCheckedChange = {
                        plant1motor = !plant1motor
                        Toast.makeText(context, "Motor is turned "+if (plant1motor){"on"}else{"off"}, Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier
                        .padding(end = 18.dp)
                        .size(8.dp)
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        if(plant2 != null){
            Column(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .width(180.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .border(
                        BorderStroke(
                            width = 1.dp,
                            color = SecondaryVariant
                        ), // Border customization
                        shape = RoundedCornerShape(12.dp) // Match corner shape of clip
                    )
                    .padding(14.dp)
            ) {
                Text(
                    text = viewModel.SubscribeToPlant(plant2.name).collectAsState().value!!,
                    modifier = Modifier.padding(bottom = 4.dp),
                    color = Secondary,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = plant2.name,
                    modifier = Modifier.padding(bottom = 4.dp),
                    color = Secondary,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(1F)
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Motor",
                        modifier = Modifier.padding(bottom = 4.dp),
                        color = Secondary
                    )
                    Switch(
                        checked = plant2motor,
                        onCheckedChange = {
                            plant2motor = !plant2motor
                            Toast.makeText(context, "Motor is turned "+if (plant2motor){"on"}else{"off"}, Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier
                            .padding(end = 18.dp)
                            .size(8.dp)
                    )
                }
            }
        }
    }
}