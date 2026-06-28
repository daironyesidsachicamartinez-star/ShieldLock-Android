package com.shiekdlock.app.screens

import android.content.pm.PackageManager
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.shiekdlock.app.data.PreferencesManager

import com.shiekdlock.app.model.AppUiState

import android.content.Intent

import androidx.compose.material3.OutlinedTextField

import androidx.compose.runtime.LaunchedEffect

import androidx.compose.foundation.Image
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.compose.foundation.layout.size

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight

import com.shiekdlock.app.ui.components.AppItem

import com.shiekdlock.app.ui.components.ShieldHeader

import com.shiekdlock.app.ui.components.SearchBar

import com.shiekdlock.app.ui.components.ShieldButton

import com.shiekdlock.app.ui.components.SectionTitle

import com.shiekdlock.app.data.AppRepository


fun drawableToBitmap(
    drawable: android.graphics.drawable.Drawable
): Bitmap {

    val bitmap = Bitmap.createBitmap(
        drawable.intrinsicWidth,
        drawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )

    val canvas = Canvas(bitmap)

    drawable.setBounds(
        0,
        0,
        canvas.width,
        canvas.height
    )

    drawable.draw(canvas)

    return bitmap
}

@Composable
fun HomeScreen(
    onOpenPinScreen: () -> Unit
) {

    val context = LocalContext.current

    val preferencesManager = remember {
        PreferencesManager(context)
    }

    val repository = remember {
    AppRepository(context)
}

    var apps by remember {
    mutableStateOf(listOf<AppUiState>())
}

    var searchText by remember {
    mutableStateOf("")
}

    var protectedPackages by remember {
        mutableStateOf(
            preferencesManager.getProtectedApps()
        )
    }

    LaunchedEffect(protectedPackages) {

    apps = repository.loadApps(
        protectedPackages
    )

}
    Column(
    modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
) {

       ShieldHeader(

    protectedApps =
        protectedPackages.size

)

Spacer(
    modifier = Modifier.height(16.dp)
)

        

        

        ShieldButton(

    text = "Configurar PIN",

    onClick = {

        onOpenPinScreen()

    }

)

SearchBar(

    value = searchText,

    onValueChange = {

        searchText = it

    }

)

SectionTitle(

    title = "Aplicaciones"

)


        LazyColumn {

            items(

    apps.filter {

        it.name.contains(
            searchText,
            ignoreCase = true
        )

    }

) { app ->

                AppItem(
    app = app,
    checked = app.protected,
    onCheckedChange = { checked ->

        protectedPackages =
            if (checked) {
                protectedPackages + app.packageName
            } else {
                protectedPackages - app.packageName
            }

        preferencesManager.saveProtectedApps(
            protectedPackages
        )
    }
)