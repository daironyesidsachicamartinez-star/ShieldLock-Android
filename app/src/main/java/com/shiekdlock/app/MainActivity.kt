package com.shiekdlock.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.shiekdlock.app.screens.HomeScreen
import com.shiekdlock.app.screens.PinScreen
import com.shiekdlock.app.screens.SettingsScreen
import com.shiekdlock.app.ui.theme.ShiekdlockTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            var currentScreen by remember {
                mutableStateOf("home")
            }

            ShiekdlockTheme {

                Scaffold(

                    modifier = Modifier.fillMaxSize(),

                    bottomBar = {

                        if (currentScreen != "pin") {

                            NavigationBar {

                                NavigationBarItem(

                                    selected = currentScreen == "home",

                                    onClick = {
                                        currentScreen = "home"
                                    },

                                    icon = {
                                        Icon(
                                            Icons.Default.Home,
                                            contentDescription = null
                                        )
                                    },

                                    label = {
                                        Text("Inicio")
                                    }

                                )

                                NavigationBarItem(

                                    selected = currentScreen == "settings",

                                    onClick = {
                                        currentScreen = "settings"
                                    },

                                    icon = {
                                        Icon(
                                            Icons.Default.Settings,
                                            contentDescription = null
                                        )
                                    },

                                    label = {
                                        Text("Ajustes")
                                    }

                                )

                            }

                        }

                    }

                ) { _ ->

                    when (currentScreen) {

                        "home" -> {

                            HomeScreen(
                                onOpenPinScreen = {
                                    currentScreen = "pin"
                                }
                            )

                        }

                        "settings" -> {

                            SettingsScreen(
                                onOpenPin = {
                                    currentScreen = "pin"
                                }
                            )

                        }

                        "pin" -> {

                            PinScreen(
                                onBack = {
                                    currentScreen = "settings"
                                }
                            )

                        }

                    }

                }

            }

        }

    }

}