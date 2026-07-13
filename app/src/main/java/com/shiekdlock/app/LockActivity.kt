package com.shiekdlock.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.shiekdlock.app.data.PreferencesManager
import com.shiekdlock.app.screens.LockScreen
import com.shiekdlock.app.ui.theme.ShiekdlockTheme

import android.content.Intent

class LockActivity : ComponentActivity() {

    private lateinit var preferencesManager: PreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        preferencesManager =
            PreferencesManager(this)

        preferencesManager.setLockScreenShowing(true)

        setContent {

            ShiekdlockTheme {

                LockScreen(

                    onUnlocked = {

                        val packageName =
                            preferencesManager
                                .getLastProtectedApp()

                        preferencesManager
                            .setLockScreenShowing(false)

                        val launchIntent =
                            packageManager
                                .getLaunchIntentForPackage(
                                    packageName
                                )

                        if (launchIntent != null) {

                            startActivity(
                                launchIntent
                            )

                        }

                        finish()

                    }

                )

            }

        }

    }
      
    override fun onDestroy() {

        preferencesManager
            .setLockScreenShowing(false)

        super.onDestroy()

    }

}