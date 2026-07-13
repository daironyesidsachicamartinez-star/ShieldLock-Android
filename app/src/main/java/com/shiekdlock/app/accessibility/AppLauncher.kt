package com.shiekdlock.app.accessibility

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import com.shiekdlock.app.LockActivity

class AppLauncher {

    fun launchLock(
        service: AccessibilityService
    ) {

        val intent = Intent(
            service.applicationContext,
            LockActivity::class.java
        )

        intent.addFlags(
            Intent.FLAG_ACTIVITY_NEW_TASK
        )

        intent.addFlags(
            Intent.FLAG_ACTIVITY_SINGLE_TOP
        )

        service.startActivity(intent)

    }

}