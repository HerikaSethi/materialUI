package com.example.multiformsui.screens

import android.animation.ObjectAnimator
import android.app.AlertDialog
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.multiformsui.R
import com.example.multiformsui.databinding.FragmentScreenFourBinding


class ScreenFourFragment : Fragment() {
    lateinit var binding: FragmentScreenFourBinding
    lateinit var firstNameText: String
    lateinit var lastNameText:String
    var isValidatedChecked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentScreenFourBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.backBtn.setOnClickListener { findNavController().navigate(R.id.action_screenFourFragment_to_screenThreeFragment) }

        progressbar()
        firstNamefocusListener()
        lastNamefocusListener()


        //-------------------------Submit button----------------------------
        //when next button clicked and validations are either ok or not
        binding.submitBtn.setOnClickListener {
            submitPage()
        }

        //pull to refresh
        val swipeToRefreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.swipeToRefresh)
        swipeToRefreshLayout.setOnRefreshListener{
            Toast.makeText(requireContext(),"Refreshed Page",Toast.LENGTH_SHORT).show()
            swipeToRefreshLayout.isRefreshing = false
        }

        //-------------------------Next button------------------------------
        binding.nextBtn.setOnClickListener {
            if (isValidatedChecked){ findNavController().navigate(R.id.action_screenFourFragment_to_screenFiveFragment)}
            else{ Toast.makeText(requireContext(),"Please fill the details", Toast.LENGTH_SHORT).show() }
        }


    }


    private fun submitPage() {
        //if helper text has null (returned) i.e no corrections needed
        val validFirstName = binding.FirstNameContainer.helperText == null
        val validLastName = binding.lastNameContainer.helperText == null

        if(validFirstName && validLastName) {
            resetform()
            isValidatedChecked = true
        }
        else{
            invalidform()
            isValidatedChecked = false
        }

    }

    private fun resetform() {
        var message = "First Name: "+binding.firstNameEditText.text
        message += "\nLast Name: "+binding.lastNameEditText.text

        AlertDialog.Builder(requireContext())
            .setTitle("Form Submitted")
            .setMessage(message)
            .setPositiveButton("Ok"){_,_->
                //clearing our textboxes back to blank
                binding.firstNameEditText.text = null
                binding.lastNameEditText.text = null

            }.show()
    }

    private fun invalidform() {
        var message = ""
        if(binding.FirstNameContainer.helperText !=null)
            message += "\n\nFirst Name:"+binding.FirstNameContainer.helperText
        if (binding.lastNameContainer.helperText !=null)
            message += "\n\nLast Name:"+binding.lastNameContainer.helperText

        AlertDialog.Builder(requireContext())
            .setTitle("Invalid Form")
            .setMessage(message)
            .setPositiveButton("Ok"){_,_->
            }.show()

    }

    //-------------------------First name-----------------------------------
    private fun firstNamefocusListener() {
        binding.firstNameEditText.setOnFocusChangeListener {_,focused ->
            if (!focused){
                binding.FirstNameContainer.helperText = validfirstName()
            }
        }
    }

    private fun validfirstName(): String? {
        firstNameText = binding.firstNameEditText.text.toString()
        if (firstNameText.matches(".*[@#$/&].*".toRegex())){
            return "Spcl chars not allowed"
        }
        if(firstNameText.matches(".*[0-9].*".toRegex())){
            return "Must not contain no"
        }
        return null
    }

    //-----------------------Last Name------------------------------
    private fun lastNamefocusListener() {
        binding.lastNameEditText.setOnFocusChangeListener {_,focused ->
            if (!focused){
                binding.lastNameContainer
                    .helperText = validLastName()
            }
        }
    }

    private fun validLastName(): String? {
        lastNameText = binding.lastNameEditText.text.toString()
        //if length not 10
        if (lastNameText.matches(".*[@#$/&].*".toRegex())){
            return "Special characters not allowed"
        }
        if(lastNameText.matches(".*[0-9].*".toRegex())){
            return "Name must not contain numbers"
        }

        return null
    }



    //----------------------progressbar-----------------------------
    private fun progressbar() {
        binding.progressBar.max = 1000
        val currentProgress = 800

        ObjectAnimator.ofInt(binding.progressBar,"progress",currentProgress)
            .setDuration(2000)
            .start()
    }


}