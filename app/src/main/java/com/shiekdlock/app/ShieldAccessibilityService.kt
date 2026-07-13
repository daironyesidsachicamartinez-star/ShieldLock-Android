package com.shiekdlock.app

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import com.shiekdlock.app.data.PreferencesManager

import com.shiekdlock.app.accessibility.AppBlocker

import com.shiekdlock.app.accessibility.AppLauncher

class ShieldAccessibilityService :
    AccessibilityService() {

    private lateinit var preferencesManager:
        PreferencesManager

    private lateinit var appBlocker:
    AppBlocker

    private lateinit var appLauncher:
    AppLauncher

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

            appBlocker =
    AppBlocker(
        preferencesManager
    )

    appLauncher =
    AppLauncher()

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
    isSystemPackage(packageName)
) {

    resetState()

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

    if (
    !appBlocker.isProtectedApp(
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

    preferencesManager
    .saveLastProtectedApp(
        packageName
    )

if (
    isLaunchingLock
) {

    Log.d(
        "ShieldLock",
        "Actualizando app protegida: $packageName"
    )

    return
}

isLaunchingLock = true

Log.d(
    "ShieldLock",
    "APP PROTEGIDA DETECTADA: $packageName"
)
    appLauncher.launchLock(this)

}

private fun isSystemPackage(
    packageName: String
): Boolean {

    return packageName ==
            "com.sec.android.app.launcher"

            ||

            packageName ==
            "com.android.systemui"

}

private fun resetState() {

    lastPackage = ""

    isLaunchingLock = false

    Log.d(
        "ShieldLock",
        "Usuario salió de la aplicación"
    )

}

override fun onInterrupt() {

}

    }
