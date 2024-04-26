package com.srinand.agrocare

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.srinand.agrocare.ui.pages.navigation.AppNavHost
import com.srinand.agrocare.ui.pages.navigation.Routes
import com.srinand.agrocare.ui.theme.AgroCareTheme
import com.srinand.agrocare.ui.theme.Primary
import com.srinand.agrocare.viewmodels.MQTTClientViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AgroCareTheme {
                val viewModel = hiltViewModel<MQTTClientViewModel>()
                val isfirst = viewModel.getIsFirst()
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (isfirst){
                        AppNavHost(startDestination = Routes.StartUpPage.route)
                    }else{
                        AppNavHost(startDestination = Routes.MainPage.route)
                    }
                }
            }
        }
    }
}
