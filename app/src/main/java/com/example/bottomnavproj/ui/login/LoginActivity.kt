package com.example.bottomnavproj.ui.login

import android.content.Intent
import com.example.bottomnavproj.data.storage.TokenStorage
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.bottomnavproj.ui.home.HomeActivity
import com.example.bottomnavproj.databinding.ActivityLoginBinding
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            val textWatcher = object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    updateLoginButtonState()
                }
                override fun afterTextChanged(s: Editable?) {}
            }

            usernameInput?.addTextChangedListener(textWatcher)
            passwordInput?.addTextChangedListener(textWatcher)

            // Initial button state
            updateLoginButtonState()
        }

        binding.loginButton?.setOnClickListener {
            lifecycleScope.launch {
                TokenStorage.saveToken(this@LoginActivity, "TOKEN_HERE")
                // After token is saved, navigate to MainActivity
                startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                finish() // Finish LoginActivity to avoid going back to it
            }
        }
    }

    private fun updateLoginButtonState() {
        val username = binding.usernameInput?.text.toString()
        val password = binding.passwordInput?.text.toString()
        binding.loginButton?.isEnabled = username.isNotBlank() && password.isNotBlank()
    }
}
