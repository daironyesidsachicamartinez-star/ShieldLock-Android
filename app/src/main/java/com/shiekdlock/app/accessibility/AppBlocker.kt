package com.shiekdlock.app.accessibility

import com.shiekdlock.app.data.PreferencesManager

class AppBlocker(

    private val preferencesManager: PreferencesManager

) {

    fun isProtectedApp(
        packageName: String
    ): Boolean {

        return preferencesManager
            .getProtectedApps()
            .contains(packageName)

    }

}