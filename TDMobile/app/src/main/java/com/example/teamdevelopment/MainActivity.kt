package com.example.teamdevelopment

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import com.example.teamdevelopment.domain.useCases.user.UserReadQRUseCase
import com.example.teamdevelopment.ui.theme.TeamDevelopmentTheme
import com.example.teamdevelopment.navigation.Navigation
import com.example.teamdevelopment.network.Network
import com.example.teamdevelopment.network.repository.UserRepository
import com.example.teamdevelopment.view.MainScreens.MainScreen.Composables.ScanFunctionAmbient
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val context: Context = this
        val scanLauncher = registerForActivityResult(ScanContract()) { result ->
            if (result.contents == null) {

            } else {
                GlobalScope.launch {
                    try {
                        UserReadQRUseCase(UserRepository(Network.getUserApi())).invoke(result.contents)
                        withContext(Dispatchers.Main) {
                            Toast.makeText(context, "Успешная передача ключа", Toast.LENGTH_LONG).show()
                        }
                    } catch (e: Exception) {
                    }
                }
            }
        }

        fun scanqr() {
            scanLauncher.launch(ScanOptions())
        }
        setContent {
            CompositionLocalProvider(ScanFunctionAmbient provides ::scanqr) {
                TeamDevelopmentTheme {
                    Surface(color = MaterialTheme.colorScheme.background) {
                        Navigation()
                    }
                }
            }
        }
    }

}