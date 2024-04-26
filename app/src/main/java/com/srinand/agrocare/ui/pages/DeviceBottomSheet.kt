//package com.abhiram.agrocare.ui.pages
//
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.rounded.DeviceThermostat
//import androidx.compose.material.icons.rounded.PropaneTank
//import androidx.compose.material.icons.rounded.Water
//import androidx.compose.material.icons.rounded.WaterDrop
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.Icon
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.ModalBottomSheet
//import androidx.compose.material3.Switch
//import androidx.compose.material3.SwitchDefaults
//import androidx.compose.material3.Text
//import androidx.compose.material3.rememberModalBottomSheetState
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.hilt.navigation.compose.hiltViewModel
//import com.srinand.agrocare.ui.theme.FadedBorder
//import com.srinand.agrocare.ui.theme.Secondary
//import com.srinand.agrocare.viewmodels.MQTTClientViewModel
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun DeviceBottomSheet() {
//    val viewModel = hiltViewModel<MQTTClientViewModel>()
//    val device = viewModel.selectedDevice
//    val bottomSheetState = rememberModalBottomSheetState()
//    val motorRunning = viewModel.motor
//
//    ModalBottomSheet(
//        onDismissRequest = {
//            viewModel.showDeviceSheet = false
//            viewModel.UnSubscribeToDevice(device)
//                           },
//        sheetState = bottomSheetState,
//        containerColor = Secondary
//    ) {
//        viewModel.SubscribeToDevice(device)
//        Column(
//            modifier = Modifier
//                .fillMaxWidth(1F)
//                .padding(horizontal = 24.dp)
//                .padding(bottom = 32.dp)
//        ) {
//            Text(
//                text = device.capitalize(),
//                style = MaterialTheme.typography.titleSmall,
//                fontWeight = FontWeight.SemiBold
//            )
//            Text(
//                text = "Online",
//                fontWeight = FontWeight.SemiBold,
//                modifier = Modifier.padding(top = 2.dp, bottom = 12.dp),
//                color = FadedBorder
//            )
//            Row(
//                modifier = Modifier.fillMaxWidth(1F),
//                horizontalArrangement = Arrangement.SpaceEvenly
//            ) {
//                Column (
//                    modifier = Modifier
//                        .weight(1f)
//                        .padding(vertical = 12.dp),
//                    verticalArrangement = Arrangement.Center,
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    Text(
//                        text = "Moisture",
//                        style = MaterialTheme.typography.bodyLarge,
//                        color = FadedBorder,
//                        modifier = Modifier.padding(bottom = 8.dp)
//                    )
//                    Row(
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        Icon(
//                            imageVector = Icons.Rounded.Water,
//                            contentDescription = "Moisture",
//                            modifier = Modifier.size(24.dp)
//                        )
//                        Spacer(modifier = Modifier.width(8.dp))
//                        Text(text = viewModel.moisture)
//                    }
//                }
//                Column(
//                    modifier = Modifier
//                        .weight(1f)
//                        .padding(vertical = 12.dp),
//                    verticalArrangement = Arrangement.Center,
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    Text(
//                        text = "Temperature",
//                        style = MaterialTheme.typography.bodyLarge,
//                        color = FadedBorder,
//                        modifier = Modifier.padding(bottom = 8.dp)
//                    )
//                    Row(
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        Icon(
//                            imageVector = Icons.Rounded.DeviceThermostat,
//                            contentDescription = "Temperature",
//                            modifier = Modifier.size(24.dp)
//                        )
//                        Spacer(modifier = Modifier.width(8.dp))
//                        Text(text = viewModel.temperature)
//                    }
//                }
//            }
//            Row(
//                modifier = Modifier.fillMaxWidth(1F),
//                horizontalArrangement = Arrangement.SpaceEvenly
//            )  {
//                Column(
//                    modifier = Modifier
//                        .weight(1f)
//                        .padding(vertical = 12.dp),
//                    verticalArrangement = Arrangement.Center,
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    Text(
//                        text = "Humidity",
//                        style = MaterialTheme.typography.bodyLarge,
//                        color = FadedBorder,
//                        modifier = Modifier.padding(bottom = 8.dp)
//                    )
//                    Row(
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        Icon(
//                            imageVector = Icons.Rounded.WaterDrop,
//                            contentDescription = "Humidity",
//                            modifier = Modifier.size(24.dp)
//                        )
//                        Spacer(modifier = Modifier.width(8.dp))
//                        Text(text = viewModel.humidity)
//                    }
//                }
//                Column(
//                    modifier = Modifier
//                        .weight(1f)
//                        .padding(vertical = 12.dp),
//                    verticalArrangement = Arrangement.Center,
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ){
//                    Text(
//                        text = "WaterLevel",
//                        style = MaterialTheme.typography.bodyLarge,
//                        color = FadedBorder,
//                        modifier = Modifier.padding(bottom = 8.dp)
//                    )
//                    Row(
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        Icon(
//                            imageVector = Icons.Rounded.PropaneTank,
//                            contentDescription = "WaterLevel",
//                            modifier = Modifier.size(24.dp)
//                        )
//                        Spacer(modifier = Modifier.width(8.dp))
//                        Text(text = viewModel.waterlevel)
//                    }
//                }
//            }
////            Row(
////                modifier = Modifier.fillMaxWidth(1F).padding(top = 24.dp).padding(vertical = 8.dp, horizontal = 32.dp),
////                verticalAlignment = Alignment.CenterVertically
////            ) {
////                Text(
////                    text = "Motor",
////                    style = MaterialTheme.typography.bodyLarge,
////                    color = FadedBorder,
////                    modifier = Modifier.weight(1f)
////                )
////                Switch(
////                    checked =  viewModel.motor,
////                    onCheckedChange = {
////                        viewModel.DeviceOn(device,viewModel.motor)
////                    },
////                    modifier = Modifier.size(50.dp),
////                    colors = SwitchDefaults.colors(
////                    )
////                )
////            }
//        }
//    }
//
//}