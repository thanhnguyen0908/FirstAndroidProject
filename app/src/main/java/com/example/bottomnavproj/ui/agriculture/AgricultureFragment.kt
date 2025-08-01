package com.example.bottomnavproj.ui.agriculture

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.bottomnavproj.data.storage.TokenStorage
import com.example.bottomnavproj.databinding.FragmentAgricultureBinding
import com.example.bottomnavproj.ui.login.LoginActivity
import kotlinx.coroutines.launch

class AgricultureFragment: Fragment() {
    private lateinit var binding: FragmentAgricultureBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for the fragment
        binding = FragmentAgricultureBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.logoutButton.setOnClickListener {
            lifecycleScope.launch {
                    TokenStorage.clearToken(requireContext())
                    val intent = Intent(requireContext(), LoginActivity::class.java).apply {
                        // Clear back stack so user can't navigate back
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    }
                    startActivity(intent)
            }
        }
    }
}