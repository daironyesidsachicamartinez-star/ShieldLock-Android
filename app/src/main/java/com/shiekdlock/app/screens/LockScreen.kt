package com.shiekdlock.app.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

import com.shiekdlock.app.data.PinManager
import com.shiekdlock.app.data.PreferencesManager
import com.shiekdlock.app.ui.components.ShieldButton
import com.shiekdlock.app.ui.components.drawableToBitmap

import kotlinx.coroutines.delay

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

    val packageManager = context.packageManager

    var packageName by remember {
    mutableStateOf(
        preferencesManager.getLastProtectedApp()
    )
}

    var appName by remember {
        mutableStateOf("Aplicación protegida")
    }

    var appIcon by remember {
        mutableStateOf<android.graphics.drawable.Drawable?>(null)
    }

    LaunchedEffect(Unit) {

    while (true) {

        val currentPackage =
            preferencesManager.getLastProtectedApp()

        if (currentPackage != packageName) {

            packageName = currentPackage

        }

        try {

            val info =
                packageManager.getApplicationInfo(
                    packageName,
                    0
                )

            appName =
                packageManager
                    .getApplicationLabel(info)
                    .toString()

            appIcon =
                packageManager
                    .getApplicationIcon(info)

        } catch (_: Exception) {

        }

        delay(250)

    }

}

    var enteredPin by remember {
        mutableStateOf("")
    }

    var message by remember {
        mutableStateOf("")
    }

    var unlocking by remember {
    mutableStateOf(false)
}

    Column(

    modifier = Modifier
        .fillMaxSize()
        .systemBarsPadding()
        .padding(horizontal = 32.dp),

    horizontalAlignment = Alignment.CenterHorizontally,

    verticalArrangement = Arrangement.Center

) {

    LaunchedEffect(unlocking) {

    if (!unlocking) return@LaunchedEffect

    delay(400)

    onUnlocked()

}
                

                Icon(

    imageVector = Icons.Default.Lock,

    contentDescription = null,

    tint = MaterialTheme.colorScheme.primary,

    modifier = Modifier.size(88.dp)

)

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                appIcon?.let {

                    Image(

                        bitmap = drawableToBitmap(it).asImageBitmap(),

                        contentDescription = appName,

                        modifier = Modifier.size(64.dp)

                    )

                    Spacer(
                        modifier = Modifier.height(24.dp)
                    )

                }

                Text(

                    text = appName,

                    style = MaterialTheme.typography.titleLarge,

                    fontWeight = FontWeight.Bold

                )

                Spacer(
                    modifier = Modifier.height(6.dp)
                )

                Text(

                    text =
    if (unlocking)
        "🔓 Desbloqueando..."
    else
        "Ingrese su PIN para continuar.",

                    style = MaterialTheme.typography.bodyMedium

                )

                Spacer(
                    modifier = Modifier.height(36.dp)
                )

                OutlinedTextField(

                    value = enteredPin,

                    onValueChange = {

                        if (it.length <= 6) {

                            enteredPin = it

                            if (

                                enteredPin.length >= 4 &&
                                enteredPin == pinManager.getPin()

                            ) {

                                if (!unlocking) {

    preferencesManager.saveAppUnlockTime(
        packageName,
        System.currentTimeMillis()
    )

    message = ""

    unlocking = true

}

                            }

                        }

                    },

                    modifier = Modifier.fillMaxWidth(),

                    singleLine = true,

                    visualTransformation =
                        PasswordVisualTransformation(),

                    keyboardOptions =
                        KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),

                    label = {
                        Text("PIN")
                    }

                )

                if (

                    enteredPin.length >= 4 &&
                    enteredPin != pinManager.getPin()

                ) {

                    LaunchedEffect(enteredPin) {

                        message = "PIN incorrecto"

                        enteredPin = ""

                    }

                }

                if (message.isNotEmpty()) {

                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )

                    Text(

                        text = message,

                        color = MaterialTheme.colorScheme.error,

                        style = MaterialTheme.typography.bodyMedium

                    )

                }

            }

        }

    



