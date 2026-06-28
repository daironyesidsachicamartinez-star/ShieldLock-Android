package com.shiekdlock.app.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ShieldHeader(

    protectedApps: Int

) {

    Card(

        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(20.dp),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )

    ) {

        Column(

            modifier = Modifier.padding(20.dp)

        ) {

            Text(

                text = "🛡 ShieldLock",

                style = MaterialTheme.typography.headlineMedium,

                fontWeight = FontWeight.Bold

            )

            Spacer(

                modifier = Modifier.height(8.dp)

            )

            Text(

                text = "Protege tu privacidad de forma sencilla.",

                style = MaterialTheme.typography.bodyMedium

            )

            Spacer(

                modifier = Modifier.height(18.dp)

            )

            Text(

                text = "$protectedApps aplicaciones protegidas",

                style = MaterialTheme.typography.titleMedium,

                fontWeight = FontWeight.SemiBold

            )

        }

    }

}