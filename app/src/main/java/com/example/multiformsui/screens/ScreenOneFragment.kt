package com.example.multiformsui.screens

import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.multiformsui.R
import com.example.multiformsui.databinding.FragmentScreenOneBinding


class ScreenOneFragment : Fragment() {
    lateinit var binding: FragmentScreenOneBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScreenOneBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressbar()

        //pull to refresh
        val swipeToRefreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.swipeToRefresh)
        swipeToRefreshLayout.setOnRefreshListener{
            Toast.makeText(requireContext(),"Refreshed Page", Toast.LENGTH_SHORT).show()
            swipeToRefreshLayout.isRefreshing = false
        }

        nextfragment()

    }


    private fun progressbar() {
        binding.progressBar.max = 1000
        val currentProgress = 200

        ObjectAnimator.ofInt(binding.progressBar,"progress",currentProgress)
            .setDuration(2000)
            .start()
    }

    private fun nextfragment() {
            binding.leadGenerationBtn.setOnClickListener {
                redirect()
            }
            binding.multistepBtn.setOnClickListener {
                redirect()
            }
            binding.integrationsBtn.setOnClickListener {
            redirect()
            }
    }

    private fun redirect() {
        findNavController().navigate(R.id.action_screenOneFragment_to_screenTwoFragment)
    }


}