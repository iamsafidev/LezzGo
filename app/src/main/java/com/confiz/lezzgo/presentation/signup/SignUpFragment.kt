package com.confiz.lezzgo.presentation.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.confiz.lezzgo.R
import com.confiz.lezzgo.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {
    lateinit var binding: FragmentSignUpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_sign_up,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
               loginEmailButton.setOnClickListener { navigateToLoginFragment() }
               loginGoogleButton.setOnClickListener { navigateToLoginFragment() }
               loginMicrosoftButton.setOnClickListener { navigateToLoginFragment() }
        }
    }

    fun navigateToLoginFragment(){
        if(isAdded)
            findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToLoginFragment())
    }


}