package com.shiekdlock.app.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ShieldHeader(

    protectedApps: Int

) {

    Card(

        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(24.dp),

        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )

    ) {

        Column(

            modifier = Modifier.padding(22.dp)

        ) {

            Row(

                verticalAlignment = Alignment.CenterVertically,

                horizontalArrangement = Arrangement.spacedBy(16.dp)

            ) {

                Icon(

                    imageVector = Icons.Default.Security,

                    contentDescription = null,

                    tint = MaterialTheme.colorScheme.primary

                )

                Column {

                    Text(

                        text = "ShieldLock",

                        style = MaterialTheme.typography.headlineSmall,

                        fontWeight = FontWeight.Bold

                    )

                    Text(

                        text = "Protección inteligente para tus aplicaciones",

                        style = MaterialTheme.typography.bodyMedium

                    )

                }

            }

            Spacer(
                modifier = Modifier.height(22.dp)
            )

            Row(

                modifier = Modifier.fillMaxWidth(),

                horizontalArrangement = Arrangement.SpaceBetween

            ) {

                InfoItem(

                    title = "Protegidas",

                    value = protectedApps.toString()

                )

                InfoItem(

                    title = "Tiempo",

                    value = "1 min"

                )

                InfoItem(

                    title = "Servicio",

                    value = "Activo"

                )

            }

        }

    }

}

@Composable
private fun InfoItem(

    title: String,

    value: String

) {

    Column(

        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        Text(

            text = value,

            style = MaterialTheme.typography.titleLarge,

            fontWeight = FontWeight.Bold,

            color = MaterialTheme.colorScheme.primary

        )

        Text(

            text = title,

            style = MaterialTheme.typography.bodySmall

        )

    }

}