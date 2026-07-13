package com.shiekdlock.app

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import com.shiekdlock.app.data.PreferencesManager

class ShieldAccessibilityService :
    AccessibilityService() {

    private lateinit var preferencesManager:
        PreferencesManager

    /*
     * Última aplicación detectada.
     */
    private var lastPackage = ""

    /*
     * Evita abrir dos LockActivity
     * al mismo tiempo.
     */
    private var isLaunchingLock = false

    override fun onServiceConnected() {

        super.onServiceConnected()

        preferencesManager =
            PreferencesManager(
                applicationContext
            )

        Log.d(
            "ShieldLock",
            "Servicio iniciado"
        )

    }

    override fun onAccessibilityEvent(
        event: AccessibilityEvent?
    ) {

        if (
            event == null
        ) {
            return
        }

        if (
            event.eventType !=
            AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED
        ) {
            return
        }

        val packageName =
            event.packageName?.toString()
                ?: return

        handlePackageChange(
            packageName
        )

    }

private fun handlePackageChange(
    packageName: String
) {

    if (
        packageName ==
        applicationContext.packageName
    ) {
        return
    }

    if (

        packageName ==
        "com.sec.android.app.launcher"

        ||

        packageName ==
        "com.android.systemui"

    ) {

        lastPackage = ""

        isLaunchingLock = false

        Log.d(
            "ShieldLock",
            "Usuario salió de la aplicación"
        )

        return

    }

    if (
        packageName == lastPackage
    ) {
        return
    }

    lastPackage = packageName

    Log.d(
        "ShieldLock",
        "Nueva aplicación: $packageName"
    )

    val protectedApps =
        preferencesManager.getProtectedApps()

    if (
        !protectedApps.contains(
            packageName
        )
    ) {
        return
    }

    val lastUnlockTime =
        preferencesManager.getAppUnlockTime(
            packageName
        )

    val currentTime =
        System.currentTimeMillis()

    val unlockDuration =
        preferencesManager.getUnlockDuration()

    if (
        currentTime -
        lastUnlockTime <
        unlockDuration
    ) {

        Log.d(
            "ShieldLock",
            "App desbloqueada recientemente"
        )

        return

    }

    if (
        isLaunchingLock
    ) {
        return
    }

    isLaunchingLock = true

    preferencesManager
        .saveLastProtectedApp(
            packageName
        )

    Log.d(
        "ShieldLock",
        "APP PROTEGIDA DETECTADA: $packageName"
    )

    val intent = Intent(
        applicationContext,
        LockActivity::class.java
    )

    intent.addFlags(
        Intent.FLAG_ACTIVITY_NEW_TASK
    )

    intent.addFlags(
        Intent.FLAG_ACTIVITY_SINGLE_TOP
    )

    startActivity(intent)

}

override fun onInterrupt() {

}

    }
