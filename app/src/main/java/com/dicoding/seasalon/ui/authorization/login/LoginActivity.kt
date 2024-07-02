package com.dicoding.seasalon.ui.authorization.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.dicoding.seasalon.ui.MainActivity
import com.dicoding.seasalon.R
import com.dicoding.seasalon.data.UserModel
import com.dicoding.seasalon.databinding.ActivityLoginBinding
import com.dicoding.seasalon.ui.ViewModelFactory

class LoginActivity : AppCompatActivity() {
    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        supportActionBar?.hide()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.token.observe(this) {
            if (it != null) {
                Log.e("LoginActivity", it)
                viewModel.saveSession(UserModel(it, true, false))
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
        }

        viewModel.message.observe(this) {
            showToast(it)
        }

        binding.btnLogIn.setOnClickListener {
            loginAccount()
        }
    }

    private fun loginAccount() {
        binding.apply {
            val email = edEmail.text.toString()
            val password = edPassword.text.toString()
            if (email.isEmpty()|| password.isEmpty()) {
                showToast(getString(R.string.error_empty))
            } else {
                viewModel.loginFirebase(email, password)
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}