package com.shiekdlock.app.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions

import com.shiekdlock.app.data.PinManager
import com.shiekdlock.app.data.PreferencesManager

@Composable
fun LockScreen(
    onUnlocked: () -> Unit
) {

    val context = LocalContext.current

    val pinManager = remember {
        PinManager(context)
    }

    val preferencesManager = remember {
        PreferencesManager(context)
    }

    var enteredPin by remember {
        mutableStateOf("")
    }

    var message by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {

        Text(
            text = "🔒 ShieldLock"
        )

        OutlinedTextField(
            value = enteredPin,
            onValueChange = {
                enteredPin = it
            },
            singleLine = true,
            visualTransformation =
                PasswordVisualTransformation(),
            keyboardOptions =
                KeyboardOptions(
                    keyboardType =
                        KeyboardType.Number
                ),
            label = {
                Text("Ingrese PIN")
            }
        )

        Button(
            onClick = {

                if (enteredPin.length < 4) {

                    message =
                        "El PIN debe tener al menos 4 dígitos"

                    return@Button
                }

                if (
                    enteredPin == pinManager.getPin()
                ) {

                    val packageName =
    preferencesManager
        .getLastProtectedApp()

preferencesManager
    .saveAppUnlockTime(
        packageName,
        System.currentTimeMillis()
    )

                    onUnlocked()

                } else {

                    message = "PIN incorrecto"
                }
            }
        ) {
            Text("Desbloquear")
        }

        Text(
            text = message
        )
    }
}