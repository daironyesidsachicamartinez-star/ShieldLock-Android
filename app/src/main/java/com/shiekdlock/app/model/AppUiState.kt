package com.shiekdlock.app.model

import android.graphics.drawable.Drawable

data class AppUiState(

    val name: String,

    val packageName: String,

    val icon: Drawable?,

    val protected: Boolean

)