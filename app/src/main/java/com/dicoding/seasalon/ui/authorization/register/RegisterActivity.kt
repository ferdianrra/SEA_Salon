package com.dicoding.seasalon.ui.authorization.register

import android.accounts.Account
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.viewModels
import com.dicoding.seasalon.R
import com.dicoding.seasalon.databinding.ActivityRegisterBinding
import com.dicoding.seasalon.ui.authorization.WelcomeActivity
import com.dicoding.seasalon.ui.home.HomeViewModel

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        supportActionBar?.hide()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContentView(binding.root)

        viewModel.isSuccess.observe(this) {
            if (it) {
                val intent = Intent(this@RegisterActivity, WelcomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
        }

        viewModel.message.observe(this) {
            showToast(it)
        }

        binding.btnCreateAccount.setOnClickListener {
            registerAccount()
        }
    }

    private fun registerAccount() {
        binding.apply {
            val name = edRegisterName.text.toString()
            val email = edRegisterEmail.text.toString()
            val password = edRegisterPassword.text.toString()
            val confirmPassword = edRegisterConfirmPassword.text.toString()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                showToast(getString(R.string.error_empty))
            } else if (!password.equals(confirmPassword)) {
                showToast(getString(R.string.error_passwords_not_match))
            } else {
                viewModel.reigsterFirebase(email, password, name)
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}