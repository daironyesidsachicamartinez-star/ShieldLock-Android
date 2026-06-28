package com.shiekdlock.app

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import com.shiekdlock.app.data.PreferencesManager
import com.shiekdlock.app.screens.LockScreen
import com.shiekdlock.app.ui.theme.ShiekdlockTheme

class LockActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val preferencesManager =
            PreferencesManager(this)

        setContent {

            ShiekdlockTheme {

                LockScreen(
                    onUnlocked = {

                        val packageName =
                            preferencesManager
                                .getLastProtectedApp()

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
}