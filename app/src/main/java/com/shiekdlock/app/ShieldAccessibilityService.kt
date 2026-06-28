package com.shiekdlock.app

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.util.Log
import android.view.accessibility.AccessibilityEvent

import com.shiekdlock.app.data.PreferencesManager

class ShieldAccessibilityService :
    AccessibilityService() {

    private var lastPackageName = ""

    override fun onServiceConnected() {

        Log.d(
            "ShieldLock",
            "Servicio iniciado"
        )
    }

    override fun onAccessibilityEvent(
        event: AccessibilityEvent?
    ) {

        val packageName =
            event?.packageName?.toString()
                ?: return

       if (packageName == lastPackageName) {
    return
}

lastPackageName = packageName

        Log.d(
            "ShieldLock",
            "App abierta: $packageName"
        )

        val preferencesManager =
            PreferencesManager(
                applicationContext
            )

        val protectedApps =
            preferencesManager.getProtectedApps()

        if (
            protectedApps.contains(
                packageName
            )
        ) {

            val lastUnlockTime =
    preferencesManager
        .getAppUnlockTime(
            packageName
        )

            val currentTime =
                System.currentTimeMillis()

            val oneMinute =
                60_000L

            if (
                currentTime - lastUnlockTime
                < oneMinute
            ) {

                Log.d(
                    "ShieldLock",
                    "App desbloqueada recientemente"
                )

                return
            }

            Log.d(
                "ShieldLock",
                "APP PROTEGIDA DETECTADA: $packageName"
            )

            preferencesManager
    .saveLastProtectedApp(
        packageName
    )

            val intent = Intent(
                applicationContext,
                LockActivity::class.java
            )

            intent.addFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK
            )

            startActivity(intent)
        }
    }

    override fun onInterrupt() {

    }
}