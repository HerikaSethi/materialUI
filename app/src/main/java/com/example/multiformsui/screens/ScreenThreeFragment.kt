package com.example.multiformsui.screens

import android.animation.ObjectAnimator
import android.app.AlertDialog
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.multiformsui.R
import com.example.multiformsui.databinding.FragmentScreenThreeBinding
import kotlin.properties.Delegates


class ScreenThreeFragment : Fragment() {
    lateinit var binding: FragmentScreenThreeBinding
    lateinit var emailText: String
    lateinit var phoneText: String
    var isValidatedChecked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScreenThreeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.backBtn.setOnClickListener { findNavController().navigate(R.id.action_screenThreeFragment_to_screenTwoFragment) }

        progressbar()
        emailfocusListener()
        phonefocusListener()

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
            if (isValidatedChecked){ findNavController().navigate(R.id.action_screenThreeFragment_to_screenFourFragment)}
            else{ Toast.makeText(requireContext(),"Please fill the details",Toast.LENGTH_SHORT).show() }
        }

    }

    private fun submitPage() {
        //if helper text has null (returned) i.e no corrections needed
        val validEmail = binding.emailContainer.helperText == null
        val validPhone = binding.phoneContainer.helperText == null

        if(validEmail && validPhone) {
            resetform()
            isValidatedChecked = true
        }
        else{
            invalidform()
            isValidatedChecked = false
        }

    }

    private fun invalidform() {
        var message = ""
        if(binding.emailContainer.helperText !=null)
            message += "\n\nEmail:"+binding.emailContainer.helperText
        if (binding.phoneContainer.helperText !=null)
            message += "\n\nPhone:"+binding.phoneContainer.helperText

        AlertDialog.Builder(requireContext())
            .setTitle("Invalid Form")
            .setMessage(message)
            .setPositiveButton("Ok"){_,_->
            }.show()

    }

    private fun resetform() {
        var message = "Email: "+binding.emailEditText.text
            message += "\nPhone no: "+binding.phoneEditText.text

        AlertDialog.Builder(requireContext())
            .setTitle("Form Submitted")
            .setMessage(message)
            .setPositiveButton("Ok"){_,_->
                //clearing our textboxes back to blank
                binding.emailEditText.text = null
                binding.phoneEditText.text = null

               // binding.emailContainer.helperText = getString(R.string.required)
            }.show()
    }


    //------------------For email----------------------------------
    private fun emailfocusListener() {
        binding.emailEditText.setOnFocusChangeListener {_,focused ->
            if (!focused){
                binding.emailContainer.helperText = validemail()
            }
        }
    }

    private fun validemail(): String? {
        emailText = binding.emailEditText.text.toString()
        if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()){
            return "Invalid email address"
        }
        return null
    }

    //-----------------------For phone--------------------------------
    private fun phonefocusListener() {
        binding.phoneEditText.setOnFocusChangeListener {_,focused ->
            if (!focused){
                binding.phoneContainer.helperText = validphone()
            }
        }
    }

    private fun validphone(): String? {
        phoneText = binding.phoneEditText.text.toString()
        //if length not 10
        if (phoneText.length!=10){
            return "Length must be 10 digits"
        }
        //if entered text is not numbers rather it is string
        if(!phoneText.matches(".*[0-9].*".toRegex())){
            return "must be all digits"
        }

        return null
    }


    //----------------------progressbar-----------------------------
    private fun progressbar() {
        binding.progressBar.max = 1000
        val currentProgress = 600

        ObjectAnimator.ofInt(binding.progressBar,"progress",currentProgress)
            .setDuration(2000)
            .start()
    }


}