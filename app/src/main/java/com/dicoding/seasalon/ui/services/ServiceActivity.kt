package com.dicoding.seasalon.ui.services

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.res.Resources
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.seasalon.R
import com.dicoding.seasalon.databinding.ActivityServiceBinding
import com.dicoding.seasalon.ui.history.HistoryViewModel
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ServiceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityServiceBinding
    private val viewModel : ServiceViewModel by viewModels()
    private lateinit var auth : FirebaseAuth
    private var id = -1
    private var operationalHour = "09:00-21:00"
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityServiceBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        supportActionBar?.hide()
        id = intent.getIntExtra(EXTRA_ID, 0)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        auth = FirebaseAuth.getInstance()
        setContentView(binding.root)

        viewModel.isSuccess.observe(this) {
            if(it) {
                @Suppress("DEPRECATION")
                onBackPressed()
            }
        }

        binding.apply {
            edDate.setOnClickListener {
                showDateTimePickerDialog()
            }
            btnBooking.setOnClickListener {
                sendOrder()
            }
        }
        showServices(id)

    }

    private fun sendOrder() {
        binding.apply {
            val name = edName.text.toString()
            val number = edPhoneNumber.text.toString()
            val date = edDate.text.toString()
            val type = titleService.text.toString()
            val userId = auth.uid.toString()
            val parts = date.split(" ")
            if (name.isEmpty() || number.isEmpty() || date.isEmpty()) {
                showToast(getString(R.string.error_empty))
            } else {
                viewModel.sendHistory(parts[0], id.toString(), parts[1], type, name, userId)
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun parseOperationalHours(operationalHour: String): List<Int> {
        val parts = operationalHour.split(":", "-", ":")
        val openHour = parts[0].toInt()
        val openMinute = parts[1].toInt()
        val closeHour = parts[2].toInt()
        val closeMinute = parts[3].toInt()
        return listOf(openHour, openMinute, closeHour, closeMinute)
    }

    private fun isValidTime(
        selectedHour: Int, selectedMinute: Int,
        openHour: Int, openMinute: Int,
        closeHour: Int, closeMinute: Int
    ): Boolean {
        val selectedTime = selectedHour * 60 + selectedMinute
        val openTime = openHour * 60 + openMinute
        val closeTime = closeHour * 60 + closeMinute
        return selectedTime in openTime..closeTime
    }

    private fun showDateTimePickerDialog() {
        val calendar = Calendar.getInstance()
        val (openHour, openMinute, closeHour, closeMinute) = parseOperationalHours(operationalHour)

        DatePickerDialog(
            this,
            { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                calendar.set(year, month, dayOfMonth)
                val selectedDate = dateFormat.format(calendar.time)

                TimePickerDialog(
                    this,
                    { _: TimePicker, selectedHour: Int, selectedMinute: Int ->
                        if (isValidTime(selectedHour, selectedMinute, openHour, openMinute, closeHour, closeMinute)) {
                            val selectedTime = String.format(Locale.getDefault(), "%02d:%02d", selectedHour, selectedMinute)
                            binding.edDate.setText("$selectedDate $selectedTime")
                        } else {
                            Toast.makeText(
                                this,
                                getString(R.string.invalid_time_message, operationalHour),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    openHour, openMinute, true
                ).show()
            },
            calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun showServices(id: Int) {
        binding.apply {
            val coverServices = resources.obtainTypedArray(R.array.cover_services)
            val titleServices = resources.getStringArray(R.array.title_services)
            val descService = resources.getStringArray(R.array.desc_services)
            ivService.setImageResource(coverServices.getResourceId(id, -1))
            titleService.text = titleServices[id]
            descServices.text = descService[id]
        }
    }

    companion object {
        const val EXTRA_ID = "extra_id"
    }
}

