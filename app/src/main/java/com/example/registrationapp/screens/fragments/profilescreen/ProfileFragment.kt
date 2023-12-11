package com.example.registrationapp.screens.fragments.profilescreen

import androidx.navigation.fragment.findNavController
import com.example.registrationapp.R
import com.example.registrationapp.base.BaseFragment
import com.example.registrationapp.databinding.FragmentProfileBinding

class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {
    override fun init() {

    }

    override fun listeners() = with(binding) {
        buttonLogout.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_welcomeFragment)
        }
    }

    override fun observers() {

    }
}