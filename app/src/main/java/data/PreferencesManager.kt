package com.shiekdlock.app.data

import android.content.Context

class PreferencesManager(
    private val context: Context
) {

    private val prefs =
        context.getSharedPreferences(
            "shieldlock_prefs",
            Context.MODE_PRIVATE
        )

    fun saveProtectedApps(
        apps: Set<String>
    ) {

        prefs.edit()
            .putStringSet(
                "protected_apps",
                apps
            )
            .apply()

    }

    fun getProtectedApps(): Set<String> {

        return prefs
            .getStringSet(
                "protected_apps",
                emptySet()
            )
            ?.toSet()
            ?: emptySet()

    }

    fun saveLastProtectedApp(
        packageName: String
    ) {

        prefs.edit()
            .putString(
                "last_protected_app",
                packageName
            )
            .apply()

    }

    fun getLastProtectedApp(): String {

        return prefs.getString(
            "last_protected_app",
            ""
        ) ?: ""

    }

    fun saveAppUnlockTime(
        packageName: String,
        time: Long
    ) {

        prefs.edit()
            .putLong(
                "unlock_$packageName",
                time
            )
            .apply()

    }

    fun getAppUnlockTime(
        packageName: String
    ): Long {

        return prefs.getLong(
            "unlock_$packageName",
            0L
        )

    }

    fun setLockScreenShowing(
        showing: Boolean
    ) {

        prefs.edit()
            .putBoolean(
                "lock_showing",
                showing
            )
            .apply()

    }

    fun isLockScreenShowing(): Boolean {

        return prefs.getBoolean(
            "lock_showing",
            false
        )

    }

    fun saveUnlockDuration(
        duration: Long
    ) {

        prefs.edit()
            .putLong(
                "unlock_duration",
                duration
            )
            .apply()

    }

    fun getUnlockDuration(): Long {

        return prefs.getLong(
            "unlock_duration",
            60_000L
        )

    }

}

