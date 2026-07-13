package com.shiekdlock.app.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.shiekdlock.app.data.AppRepository
import com.shiekdlock.app.data.PreferencesManager
import com.shiekdlock.app.model.AppUiState
import com.shiekdlock.app.ui.components.AppItem
import com.shiekdlock.app.ui.components.SearchBar
import com.shiekdlock.app.ui.components.SectionTitle
import com.shiekdlock.app.ui.components.ShieldButton
import com.shiekdlock.app.ui.components.ShieldHeader
import com.shiekdlock.app.viewmodel.HomeViewModel

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

    val viewModel = remember {
        HomeViewModel()
    }

    var apps by remember {
        mutableStateOf(emptyList<AppUiState>())
    }

    var protectedPackages by remember {
        mutableStateOf(preferencesManager.getProtectedApps())
    }

    LaunchedEffect(protectedPackages) {
        apps = repository.loadApps(protectedPackages)
    }

    val filteredApps = apps.filter {
        it.name.contains(
            viewModel.searchText,
            ignoreCase = true
        )
    }

    val protectedApps = filteredApps.filter { it.protected }
    val otherApps = filteredApps.filterNot { it.protected }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .padding(16.dp)
    ) {
        ShieldHeader(
            protectedApps = protectedPackages.size
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        ShieldButton(
            text = "Configurar PIN",
            onClick = onOpenPinScreen
        )

        SearchBar(
            value = viewModel.searchText,
            onValueChange = viewModel::onSearchChange
        )

        SectionTitle(
            title = "Aplicaciones protegidas"
        )

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(
                protectedApps,
                key = { it.packageName }
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
            }

            if (otherApps.isNotEmpty()) {
                item {
                    Spacer(
                        modifier = Modifier.height(6.dp)
                    )

                    SectionTitle(
                        title = "Otras aplicaciones"
                    )
                }
            }

            items(
                otherApps,
                key = { it.packageName }
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
            }
        }
    }
}
