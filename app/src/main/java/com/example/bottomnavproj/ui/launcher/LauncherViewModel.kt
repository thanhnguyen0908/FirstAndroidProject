package com.example.bottomnavproj.ui.launcher

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.bottomnavproj.data.storage.TokenStorage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LauncherViewModel(application: Application) : AndroidViewModel(application) {

    sealed class NavigationTarget {
        data object Login : NavigationTarget()
        data object Home : NavigationTarget()
    }

    private val _navigateTo = MutableLiveData<NavigationTarget>()
    val navigateTo: LiveData<NavigationTarget> = _navigateTo

    val isReady = MutableLiveData(false)

    init {
        viewModelScope.launch {
            delay(500)
            val token = TokenStorage.getToken(application.applicationContext)

            _navigateTo.value = if (token.isNullOrEmpty()) {
                NavigationTarget.Login
            } else {
                NavigationTarget.Home
            }

            isReady.value = true
        }
    }
}
