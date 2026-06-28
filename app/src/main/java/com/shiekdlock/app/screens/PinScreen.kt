package com.shiekdlock.app.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*

import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import androidx.compose.ui.platform.LocalContext

import com.shiekdlock.app.data.PinManager

@Composable
fun PinScreen(
    onBack: () -> Unit
) {

    val context = LocalContext.current

    val pinManager = remember {
        PinManager(context)
    }

    var pin by remember {
        mutableStateOf(
            pinManager.getPin()
        )
    }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {

        Text(
            text = "Configuración de PIN"
        )
        Button(
    onClick = {
        onBack()
    }
) {
    Text("Volver")
}

        OutlinedTextField(
            value = pin,
            onValueChange = {
                pin = it
            },
            label = {
                Text("PIN")
            }
        )

        Button(
            onClick = {
                pinManager.savePin(pin)
            }
        ) {
            Text("Guardar PIN")
        }
    }
}