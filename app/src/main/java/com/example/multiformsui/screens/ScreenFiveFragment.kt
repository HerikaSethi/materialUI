package com.example.multiformsui.screens

import android.animation.ObjectAnimator
import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.multiformsui.R
import com.example.multiformsui.databinding.FragmentScreenFiveBinding
import com.example.multiformsui.databinding.FragmentScreenFourBinding


class ScreenFiveFragment : Fragment() {
    lateinit var binding: FragmentScreenFiveBinding
    lateinit var companyNameText: String
    lateinit var jobTitleText:String
    var isValidatedChecked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScreenFiveBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.backBtn.setOnClickListener { findNavController().navigate(R.id.action_screenFiveFragment_to_screenFourFragment2) }

        progressbar()
        companyNamefocusListener()
        jobTitlefocusListener()

        //-------------------------Submit button----------------------------
        //when next button clicked and validations are either ok or not
        binding.submitBtn.setOnClickListener {
            submitPage()
        }

        val swipeToRefreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.swipeToRefresh)
        swipeToRefreshLayout.setOnRefreshListener{
            Toast.makeText(requireContext(),"Refreshed Page",Toast.LENGTH_SHORT).show()
            swipeToRefreshLayout.isRefreshing = false
        }


    }

    private fun submitPage() {
        //if helper text has null (returned) i.e no corrections needed
        val validCompanyName = binding.CompanyNameContainer.helperText == null
        val validJobTitle = binding.jobTitleContainer.helperText == null

        if(validCompanyName && validJobTitle) {
            resetform()
            isValidatedChecked = true
        }
        else{
            invalidform()
            isValidatedChecked = false
        }

    }

    private fun resetform() {
        var message = "Company Name: "+binding.companyNameEditText.text
        message += "\nJob Title: "+binding.jobTitleEditText.text

        AlertDialog.Builder(requireContext())
            .setTitle("Form Submitted")
            .setMessage(message)
            .setPositiveButton("Ok"){_,_->

                //clearing our textboxes back to blank
                binding.companyNameEditText.text = null
                binding.jobTitleEditText.text = null

            }.show()

        Toast.makeText(requireContext(),"Thanks! will get back to you soon",Toast.LENGTH_LONG).show()
    }

    private fun invalidform() {
        var message = ""
        if(binding.CompanyNameContainer.helperText !=null)
            message += "\n\nCompany Name:"+binding.CompanyNameContainer.helperText
        if (binding.jobTitleContainer.helperText !=null)
            message += "\n\nJob Title:"+binding.jobTitleContainer.helperText

        AlertDialog.Builder(requireContext())
            .setTitle("Invalid Form")
            .setMessage(message)
            .setPositiveButton("Ok"){_,_->
            }.show()

    }

    //-------------------------Company name-----------------------------------
    private fun companyNamefocusListener() {
        binding.companyNameEditText.setOnFocusChangeListener {_,focused ->
            if (!focused){
                binding.CompanyNameContainer.helperText = validcompanyName()
            }
        }
    }

    private fun validcompanyName(): String? {
        companyNameText = binding.companyNameEditText.text.toString()
        if (companyNameText.matches(".*[@#$/&].*".toRegex())){
            return "Spcl chars not allowed"
        }
        if (companyNameText.matches(".*[ ].*".toRegex())){
            return "Blank not allowed"
        }
        return null
    }

    //-----------------------Job Title-----------------------------
    private fun jobTitlefocusListener() {
        binding.jobTitleEditText.setOnFocusChangeListener {_,focused ->
            if (!focused){
                binding.jobTitleContainer
                    .helperText = validJobTitle()
            }
        }
    }

    private fun validJobTitle(): String? {
        jobTitleText = binding.jobTitleEditText.text.toString()
        //if length not 10
        if (jobTitleText.matches(".*[@#$/&].*".toRegex())){
            return "Special characters not allowed"
        }
        if (jobTitleText.matches(".*[ ].*".toRegex())){
            return "Blank not allowed"
        }
        return null
    }


    //----------------------progressbar-----------------------------
    private fun progressbar() {
        binding.progressBar.max = 1000
        val currentProgress = 1000

        ObjectAnimator.ofInt(binding.progressBar,"progress",currentProgress)
            .setDuration(2000)
            .start()
    }

}