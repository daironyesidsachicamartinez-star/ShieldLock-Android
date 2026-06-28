package com.shiekdlock.app.data

import android.content.Context

class PinManager(
    private val context: Context
) {

    private val prefs =
        context.getSharedPreferences(
            "shieldlock_pin",
            Context.MODE_PRIVATE
        )

    fun savePin(pin: String) {

        prefs.edit()
            .putString(
                "user_pin",
                pin
            )
            .apply()
    }

    fun getPin(): String {

        return prefs.getString(
            "user_pin",
            "1234"
        ) ?: "1234"
    }
}