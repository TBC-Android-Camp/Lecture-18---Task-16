package com.example.registrationapp.screens.fragments.welcomescreen

import androidx.navigation.Navigation
import com.example.registrationapp.R
import com.example.registrationapp.base.BaseFragment
import com.example.registrationapp.databinding.FragmentWelcomeBinding

class WelcomeFragment : BaseFragment<FragmentWelcomeBinding>(FragmentWelcomeBinding::inflate) {
    override fun init() {}

    override fun listeners() {
        with(binding) {

            buttonRegister.setOnClickListener { navigateToRegister() }

            buttonLogin.setOnClickListener { navigateToLogin() }

        }
    }

    override fun observers() {}

    private fun navigateToRegister() =
        Navigation.findNavController(requireView()).apply {
        navigate(R.id.action_welcomeFragment_to_registerFragment)
    }


    private fun navigateToLogin() =
        Navigation.findNavController(requireView()).apply {
            navigate(R.id.action_welcomeFragment_to_loginFragment)
        }

}