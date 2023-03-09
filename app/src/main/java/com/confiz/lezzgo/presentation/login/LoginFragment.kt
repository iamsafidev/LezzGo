package com.confiz.lezzgo.presentation.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.confiz.lezzgo.R
import com.confiz.lezzgo.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    lateinit var binding: FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate<FragmentLoginBinding>(
            inflater,
            R.layout.fragment_login,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            tvNoAccount.setOnClickListener {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToSignUpFragment())
            }

            loginButton.setOnClickListener {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToDashboardFragment2())
            }
        }
    }
}