package com.shiekdlock.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier

import com.shiekdlock.app.screens.HomeScreen
import com.shiekdlock.app.screens.PinScreen
import com.shiekdlock.app.screens.LockScreen
import com.shiekdlock.app.ui.theme.ShiekdlockTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            var currentScreen by remember {
                mutableStateOf("lock")
            }

            ShiekdlockTheme {

                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { _ ->

                    when (currentScreen) {

                        "lock" -> {

                            LockScreen(
                                onUnlocked = {
                                    currentScreen = "home"
                                }
                            )

                        }

                        "home" -> {

                            HomeScreen(
                                onOpenPinScreen = {
                                    currentScreen = "pin"
                                }
                            )

                        }

                        "pin" -> {

                            PinScreen(
                                onBack = {
                                    currentScreen = "home"
                                }
                            )

                        }
                    }
                }
            }
        }
    }
}