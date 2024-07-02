package com.dicoding.seasalon.ui.formfeedback

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.seasalon.R
import com.dicoding.seasalon.databinding.ActivityFeedbackBinding
import org.checkerframework.checker.units.qual.m

class FeedbackActivity : AppCompatActivity() {
    private val viewModel: FeedbackViewModel by viewModels()
    private lateinit var binding: ActivityFeedbackBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        supportActionBar?.hide()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        binding = ActivityFeedbackBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel.isSuccess.observe(this) {isSuccess ->
            if (isSuccess) {
                showToast(getString(R.string.feedback_complete))
                @Suppress("DEPRECATION")
                onBackPressed()
            }
        }

        binding.btnSubmit.setOnClickListener {
            sendFeedback()
        }
    }

    private fun sendFeedback() {
        binding.apply {
            val name = edNameFeedback.text.toString()
            val rate = edRatingFeedback.text.toString()
            val comment = edCommentReview.text.toString()
            if (name.isEmpty() || rate.isEmpty() || comment.isEmpty()) {
                showToast(getString(R.string.error_empty))
            } else {
                viewModel.sendFeedback(name, rate, comment)
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}