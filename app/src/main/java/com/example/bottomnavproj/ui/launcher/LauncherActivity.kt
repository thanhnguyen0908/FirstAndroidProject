package com.example.bottomnavproj.ui.launcher

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.bottomnavproj.ui.home.HomeActivity
import com.example.bottomnavproj.ui.login.LoginActivity

class LauncherActivity : AppCompatActivity() {

    private val viewModel: LauncherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        // Keeps splash screen visible until ViewModel finishes loading
        installSplashScreen().setKeepOnScreenCondition {
            !viewModel.isReady.value!!
        }

        super.onCreate(savedInstanceState)

        // Observe where to go next
        viewModel.navigateTo.observe(this) { target ->
            val intent = Intent(this, when (target) {
                LauncherViewModel.NavigationTarget.Login -> LoginActivity::class.java
                LauncherViewModel.NavigationTarget.Home -> HomeActivity::class.java
            })
            startActivity(intent)
            finish()
        }
    }
}
