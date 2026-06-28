package com.shiekdlock.app.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SearchBar(

    value: String,

    onValueChange: (String) -> Unit

) {

    OutlinedTextField(

        value = value,

        onValueChange = onValueChange,

        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),

        singleLine = true,

        label = {

            Text("Buscar aplicación")

        }

    )

}