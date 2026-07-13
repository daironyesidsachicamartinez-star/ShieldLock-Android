package com.shiekdlock.app.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.shiekdlock.app.data.PinManager
import com.shiekdlock.app.ui.components.ShieldButton

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

        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .padding(20.dp),

        horizontalAlignment = Alignment.CenterHorizontally,

        verticalArrangement = Arrangement.Center

    ) {

        Card(

            modifier = Modifier.fillMaxWidth(),

            shape = RoundedCornerShape(24.dp),

            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            )

        ) {

            Column(

                modifier = Modifier.padding(24.dp),

                horizontalAlignment = Alignment.CenterHorizontally

            ) {

                Icon(

                    imageVector = Icons.Default.Lock,

                    contentDescription = null,

                    tint = MaterialTheme.colorScheme.primary

                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                Text(

                    text = "Configuración de PIN",

                    style = MaterialTheme.typography.headlineSmall,

                    fontWeight = FontWeight.Bold

                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(

                    text = "Configura el PIN que protegerá tus aplicaciones.",

                    style = MaterialTheme.typography.bodyMedium

                )

                Spacer(
                    modifier = Modifier.height(24.dp)
                )

                OutlinedTextField(

                    value = pin,

                    onValueChange = {

                        if (it.length <= 6) {
                            pin = it
                        }

                    },

                    modifier = Modifier.fillMaxWidth(),

                    singleLine = true,

                    label = {

                        Text("PIN")

                    }

                )

                Spacer(
                    modifier = Modifier.height(24.dp)
                )

                ShieldButton(

                    text = "Guardar PIN",

                    onClick = {

                        pinManager.savePin(pin)

                    }

                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                Button(

                    modifier = Modifier.fillMaxWidth(),

                    onClick = {

                        onBack()

                    }

                ) {

                    Text("Volver")

                }

            }

        }

    }

}

