package com.shiekdlock.app.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.shiekdlock.app.data.PreferencesManager

@Composable
fun SettingsScreen(
    onOpenPin: () -> Unit
) {

    val context = LocalContext.current

    val preferencesManager = remember {
        PreferencesManager(context)
    }

    var unlockDuration by remember {
        mutableLongStateOf(
            preferencesManager.getUnlockDuration()
        )
    }

    var showDialog by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = "⚙ Ajustes",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        SettingCard(
            icon = {
                Icon(Icons.Default.Lock, null)
            },
            title = "Seguridad",
            description = "Configura el PIN de acceso"
        ) {

            Button(
                onClick = onOpenPin
            ) {
                Text("Configurar PIN")
            }

        }

        SettingCard(
            icon = {
                Icon(Icons.Default.Timer, null)
            },
            title = "Tiempo de desbloqueo",
            description = when (unlockDuration) {
                30_000L -> "Actualmente: 30 segundos"
                60_000L -> "Actualmente: 1 minuto"
                300_000L -> "Actualmente: 5 minutos"
                600_000L -> "Actualmente: 10 minutos"
                else -> "Actualmente: 1 minuto"
            },
            onClick = {
                showDialog = true
            }
        )

        SettingCard(
            icon = {
                Icon(Icons.Default.Palette, null)
            },
            title = "Tema",
            description = "Automático"
        )

        SettingCard(
            icon = {
                Icon(Icons.Default.Info, null)
            },
            title = "ShieldLock",
            description = "Versión 1.0"
        )

    }

    if (showDialog) {

        AlertDialog(

            onDismissRequest = {
                showDialog = false
            },

            confirmButton = {

                TextButton(
                    onClick = {
                        showDialog = false
                    }
                ) {
                    Text("Cerrar")
                }

            },

            title = {
                Text("Tiempo de desbloqueo")
            },

            text = {

                Column {

                    UnlockOption(
                        "30 segundos",
                        unlockDuration == 30_000L
                    ) {
                        unlockDuration = 30_000L
                        preferencesManager.saveUnlockDuration(30_000L)
                    }

                    UnlockOption(
                        "1 minuto",
                        unlockDuration == 60_000L
                    ) {
                        unlockDuration = 60_000L
                        preferencesManager.saveUnlockDuration(60_000L)
                    }

                    UnlockOption(
                        "5 minutos",
                        unlockDuration == 300_000L
                    ) {
                        unlockDuration = 300_000L
                        preferencesManager.saveUnlockDuration(300_000L)
                    }

                    UnlockOption(
                        "10 minutos",
                        unlockDuration == 600_000L
                    ) {
                        unlockDuration = 600_000L
                        preferencesManager.saveUnlockDuration(600_000L)
                    }

                }

            }

        )

    }

}

@Composable
private fun UnlockOption(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {

    androidx.compose.foundation.layout.Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(text)

        RadioButton(
            selected = selected,
            onClick = onClick
        )

    }

}

@Composable
private fun SettingCard(

    icon: @Composable () -> Unit,

    title: String,

    description: String,

    onClick: (() -> Unit)? = null,

    content: @Composable (() -> Unit)? = null

) {

    Card(

        modifier = Modifier
            .fillMaxWidth()
            .then(
                if (onClick != null)
                    Modifier.clickable { onClick() }
                else
                    Modifier
            ),

        shape = RoundedCornerShape(18.dp),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )

    ) {

        Column(

            modifier = Modifier.padding(18.dp),

            verticalArrangement = Arrangement.spacedBy(8.dp)

        ) {

            icon()

            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium
            )

            content?.invoke()

        }

    }

}