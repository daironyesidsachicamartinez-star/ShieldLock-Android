package com.shiekdlock.app.data

import android.content.Context
import android.content.Intent
import com.shiekdlock.app.model.AppUiState

class AppRepository(
    private val context: Context
) {

    fun loadApps(
    protectedApps: Set<String>
): List<AppUiState> {

        val packageManager = context.packageManager

        val intent = Intent(
            Intent.ACTION_MAIN,
            null
        ).apply {
            addCategory(
                Intent.CATEGORY_LAUNCHER
            )
        }

        return packageManager
            .queryIntentActivities(
                intent,
                0
            )
            .map {

                AppUiState(

    name = it.loadLabel(packageManager).toString(),

    packageName = it.activityInfo.packageName,

    icon = it.loadIcon(packageManager),

    protected = protectedApps.contains(
        it.activityInfo.packageName
    )

)
            }
            .distinctBy {
                it.packageName
            }
            .sortedWith(
    compareBy<AppUiState> {
        !it.protected
    }.thenBy {
        it.name
    }
)
    }
}