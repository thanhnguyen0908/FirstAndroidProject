package com.example.bottomnavproj

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.commit
import com.example.bottomnavproj.databinding.ActivityMainBinding
import com.example.bottomnavproj.ui.agriculture.AgricultureFragment
import com.example.bottomnavproj.ui.air.AirFragment
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener {

    private lateinit var binding: ActivityMainBinding
    private val airFragment = AirFragment()
    private val agricultureFragment = AgricultureFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        updateStatusBarAppearance("AIR")
        supportFragmentManager.commit {
            add(R.id.frame_content, airFragment, "AIR")
            add(R.id.frame_content, agricultureFragment, "AGR")
            hide(agricultureFragment)
        }

        binding.bottomNav.setOnItemSelectedListener(this)
        onBackPressedDispatcher.addCallback { showDialog() }
    }

    private fun updateStatusBarAppearance(currentFragmentTag: String) {
        val window = window
        val controller = WindowInsetsControllerCompat(window, window.decorView)

        when (currentFragmentTag) {
            "AIR" -> {
                controller.isAppearanceLightStatusBars = false // white icons
            }
            "AGR" -> {
                controller.isAppearanceLightStatusBars = true // dark icons
            }
        }
    }

    private fun showDialog() {
        AlertDialog.Builder(this)
            .setTitle("Warning")
            .setMessage("You are leaving the app, continue ?")
            .setNegativeButton("No") { dialog, _ -> dialog.dismiss() }
            .setPositiveButton("Yes") { _, _ -> finish() }.show()
    }

    private fun onAirClicked() {
        supportFragmentManager.commit {
            show(airFragment)
            hide(agricultureFragment)
        }
        updateStatusBarAppearance("AIR")
    }

    private fun onAgricultureClicked() {
        supportFragmentManager.commit {
            show(agricultureFragment)
            hide(airFragment)
        }
        updateStatusBarAppearance("AGR")
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_air -> {
                onAirClicked()
                return true
            }

            R.id.nav_agriculture -> {
                onAgricultureClicked()
                return true

            }

            else -> {
                return false
            }
        }
    }
}