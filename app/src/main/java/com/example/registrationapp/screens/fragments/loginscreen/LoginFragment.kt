package com.example.registrationapp.screens.fragments.loginscreen

import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.registrationapp.R
import com.example.registrationapp.base.BaseFragment
import com.example.registrationapp.createViewModelFactory
import com.example.registrationapp.databinding.FragmentLoginBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import com.example.registrationapp.network.Result

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {
    private val viewModel: LoginViewModel by viewModels { createViewModelFactory { LoginViewModel() } }

    override fun init() {
        showLoading(false)
    }

    override fun listeners() = with(binding) {
        buttonLogin.setOnClickListener {
            val email = etUsername.text.toString()
            val password = etPassword.text.toString()

            if (inputValidation(email, password)) {
                showLoading(true)
                viewModel.login(email, password)
            }
        }
    }

    override fun observers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loginState.collectLatest {
                    when (it) {
                        is Result.Success -> {
                            showLoading(false)
                            showToast("თქვენ წარმატებით შეხვედით თქვენს ანგარიშში")
                            goToProfile()
                        }

                        is Result.Error -> {
                            showLoading(false)
                            errorState(it.isNetworkError!!)
                        }

                        is Result.Loading -> {}
                    }
                }
            }
        }
    }

    private fun goToProfile() {
        findNavController().navigate(R.id.action_loginFragment_to_profileFragment)
    }

    private fun errorState(isNetworkError: Boolean) {
        binding.progressBar.visibility = View.GONE
        if (isNetworkError) showToast("შეამოწმეთ ინტერნეტთან კავშირი")
        else showToast("რაღაც ხარვეზია, სცადეთ მოგვიანებით")
    }

    private fun inputValidation(email: String, password: String): Boolean {
        var isValid = false
        when (false) {
            isEmptyEmail(email) -> showToast("Email ცარიელია!")
            isValidEmail(email) -> showToast("Email არასწორია!")
            isEmptyPassword(password) -> showToast("Password ცარიელია!")
            else -> isValid = true
        }
        return isValid
    }

    private fun showToast(message: String) =
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun isEmptyEmail(email: String) = email.isNotEmpty()

    private fun isEmptyPassword(password: String) = password.isNotEmpty()

    private fun isValidEmail(email: String) = Patterns.EMAIL_ADDRESS.matcher(email).matches()

}