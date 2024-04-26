package com.srinand.agrocare.ui.pages

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.srinand.agrocare.data.room.PlantEvent
import com.srinand.agrocare.data.room.PlantState
import com.srinand.agrocare.ui.theme.Background
import com.srinand.agrocare.ui.theme.ButtonColor
import com.srinand.agrocare.ui.theme.Primary
import com.srinand.agrocare.ui.theme.PrimaryVariant
import com.srinand.agrocare.ui.theme.Secondary

@Composable
fun AddPlants(
    state : PlantState,
    onEvent : (PlantEvent) -> Unit,
    modifier : Modifier = Modifier
) {
    val context = LocalContext.current
    var name by remember {
        mutableStateOf("")
    }
    var moisture by remember {
        mutableStateOf("")
    }
    AlertDialog(
        modifier = modifier,
        onDismissRequest = {
            onEvent(PlantEvent.HideDialog)
            onEvent(PlantEvent.SetName(""))
            onEvent(PlantEvent.SetMoisture(""))
        },
        confirmButton = {  },
        title = {},
        containerColor = Primary,
        text = {
               Column(
                   horizontalAlignment = Alignment.CenterHorizontally
               ) {
                   Text(
                       text = "Add Plant",
                       style = MaterialTheme.typography.headlineMedium,
                       color = Color.Black,
                       fontWeight = FontWeight.SemiBold,
                       modifier = Modifier.padding(bottom = 12.dp)
                   )
                   OutlinedTextField(
                       modifier = Modifier
                           .padding(vertical = 8.dp)
                           .fillMaxWidth(1F)
                           .imePadding(),
                       value = name,
                       onValueChange = { name = it },
                       label = { Text(text = "Plant Name", fontWeight = FontWeight.Medium) },
                       colors = TextFieldDefaults.colors(
                           focusedTextColor = Color.Black,
                           unfocusedContainerColor = Color.Transparent,
                           focusedContainerColor = Color.Transparent,
                           focusedIndicatorColor = Color.Black,
                           unfocusedLabelColor = Color.Black,
                           unfocusedIndicatorColor = Color.Black,
                           unfocusedTextColor = Color.Black,
                           unfocusedPlaceholderColor = Color.Black,
                           unfocusedTrailingIconColor = Color.Black,
                           focusedLabelColor = Color.Black,
                           focusedPlaceholderColor = Color.Black,
                           focusedTrailingIconColor = Color.Black
                       )
                   )
                   OutlinedTextField(
                       modifier = Modifier
                           .padding(vertical = 8.dp)
                           .fillMaxWidth(1F)
                           .imePadding(),
                       value = moisture,
                       onValueChange = { moisture = it},
                       label = { Text(text = "Minimum Moisture", fontWeight = FontWeight.Medium) },
                       colors = TextFieldDefaults.colors(
                           focusedTextColor = Color.Black,
                           unfocusedContainerColor = Color.Transparent,
                           focusedContainerColor = Color.Transparent,
                           focusedIndicatorColor = Color.Black,
                           unfocusedLabelColor = Color.Black,
                           unfocusedIndicatorColor = Color.Black,
                           unfocusedTextColor = Color.Black,
                           unfocusedPlaceholderColor = Color.Black,
                           unfocusedTrailingIconColor = Color.Black,
                           focusedLabelColor = Color.Black,
                           focusedPlaceholderColor = Color.Black,
                           focusedTrailingIconColor = Color.Black
                       )
                   )
                   Spacer(modifier = Modifier.height(8.dp))
                   Row(
                       modifier = Modifier.fillMaxWidth(1F),
                       verticalAlignment = Alignment.CenterVertically,
                       horizontalArrangement = Arrangement.Center
                   ) {
                       Button(
                           onClick = {
                               onEvent(PlantEvent.HideDialog)
                               onEvent(PlantEvent.SetName(""))
                               onEvent(PlantEvent.SetMoisture(""))
                           },
                           modifier = Modifier,
                           colors = ButtonDefaults.buttonColors(containerColor = PrimaryVariant, contentColor = Secondary)
                       ) {
                           Text(
                               text = "Cancel",
                               modifier = Modifier
                                   .padding(vertical = 8.dp),
                               style = MaterialTheme.typography.bodyLarge
                           )
                       }
                       Spacer(modifier= Modifier.width(24.dp))
                       Button(
                           onClick = {
                               state.name = name
                               state.moisture = moisture
                               if (name.isBlank() || moisture.isBlank()){
                                   Toast.makeText(context, "Please enter values!", Toast.LENGTH_SHORT).show()
                               }else{
                                   onEvent(PlantEvent.AddPlant)
                                   Toast.makeText(context, "Plant added!", Toast.LENGTH_SHORT).show()
                                   onEvent(PlantEvent.SetName(""))
                                   onEvent(PlantEvent.SetMoisture(""))
                                   onEvent(PlantEvent.HideDialog)
                               }
                           },
                           colors = ButtonDefaults.buttonColors(containerColor = ButtonColor, contentColor = Secondary)
                       ) {
                           Text(
                               text = "Add",
                               modifier = Modifier
                                   .padding(vertical = 8.dp),
                               style = MaterialTheme.typography.bodyLarge
                           )
                       }
                   }
               }
        }
    )
}

