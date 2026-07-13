package com.shiekdlock.app.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class HomeViewModel {

    var searchText by mutableStateOf("")
        private set

    fun onSearchChange(text: String) {
        searchText = text
    }
}