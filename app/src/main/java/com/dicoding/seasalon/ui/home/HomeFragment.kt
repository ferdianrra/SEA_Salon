package com.dicoding.seasalon.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.dicoding.seasalon.R
import com.dicoding.seasalon.data.Review
import com.dicoding.seasalon.databinding.FragmentHomeBinding
import com.dicoding.seasalon.ui.formfeedback.FeedbackActivity
import com.dicoding.seasalon.ui.services.ServiceActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val viewModel: HomeViewModel by viewModels()
    private val snapHelper = LinearSnapHelper()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel.listReview.observe(viewLifecycleOwner) {listReview ->
            showReview(listReview)
        }
        binding.apply {
            btnReview.setOnClickListener {
                val intent = Intent(requireContext(), FeedbackActivity::class.java)
                startActivity(intent)
            }
            btnHaircuts.setOnClickListener {
                startServicesActivity(0)
            }
            btnManicure.setOnClickListener {
                startServicesActivity(1)
            }
            btnFacial.setOnClickListener {
                startServicesActivity(2)
            }
            btnPersonMan.setOnClickListener {
                val number = getString(R.string.thomas_number)
                callAdmin(number)
            }
            btnPersonWoman.setOnClickListener {
                val number = getString(R.string.sekar_number)
                callAdmin(number)
            }
        }

        return root
    }

    private fun callAdmin(number: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:$number")
        startActivity(intent)

    }

    private fun startServicesActivity(id: Int) {
        val intent = Intent(requireContext(), ServiceActivity::class.java)
        intent.putExtra(EXTRA_ID, id)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        viewModel.getReview()
    }

    private fun showReview(listReview : Map<String, Review>) {
        binding.rvReviews.layoutManager =LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val reviewAdapter =ListCommentAdapter(listReview.values.toList())
        binding.rvReviews.adapter = reviewAdapter
        // Memastikan agar tidak berhenti ditengah saat item digulir
        snapHelper.attachToRecyclerView(binding.rvReviews)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val EXTRA_ID = "extra_id"
    }
}