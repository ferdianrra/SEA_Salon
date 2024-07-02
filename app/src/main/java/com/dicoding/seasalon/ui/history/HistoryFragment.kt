package com.dicoding.seasalon.ui.history

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.seasalon.data.Order
import com.dicoding.seasalon.databinding.FragmentHistoryBinding
import com.google.firebase.auth.FirebaseAuth

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding
    private val viewModel: HistoryViewModel by viewModels()
    private lateinit var auth : FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = FirebaseAuth.getInstance()
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.listOrder.observe(requireActivity()) {history ->
            showHistory(history)
        }

        viewModel.getHistory()
    }

    private fun showHistory(history: Map<String, Order>) {
        val userId = auth.uid
        Log.d("HistoryFragment", "Current User ID: $userId")

        // Log original history data
        history.forEach { (key, order) ->
            Log.d("HistoryFragment", "Order Key: $key, Order UserID: ${order.userid}")
        }

        // Filter dengan userId
        val filteredHistory = history.filter { (_, order) ->
            order.userid == userId
        }

        // Convert filtered history to list and set adapter
        val filteredHistoryList = filteredHistory.values.toList()
        if (filteredHistoryList.isEmpty()) {
            binding?.tvNothingActivity?.visibility = View.VISIBLE
        } else  {
            binding?.tvNothingActivity?.visibility = View.INVISIBLE
            binding?.rvHistory?.layoutManager = LinearLayoutManager(requireContext())
            val historyAdapter = HistoryAdapter(filteredHistoryList)
            binding?.rvHistory?.adapter = historyAdapter
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}