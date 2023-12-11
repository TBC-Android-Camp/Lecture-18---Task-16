package com.example.registrationapp.screens.fragments.registerscreen

import android.annotation.SuppressLint
import android.text.method.PasswordTransformationMethod
import android.util.Patterns
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import com.example.registrationapp.R
import com.example.registrationapp.base.BaseFragment
import com.example.registrationapp.createViewModelFactory
import com.example.registrationapp.databinding.FragmentRegisterBinding
import com.example.registrationapp.network.Result
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private val viewModel: RegisterViewModel by viewModels { createViewModelFactory { RegisterViewModel() } }

    override fun init() { showLoading(false) }

    @SuppressLint("ClickableViewAccessibility")
    override fun listeners() = with(binding) {
        buttonRegister.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            if (inputValidation(email, password)) {
                showLoading(true)
                viewModel.register(email, password)
            }
        }

    }

    override fun observers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.registerState.collectLatest {
                    when (it) {
                        is Result.Success -> {
                            showLoading(false)
                            navigateToLogin()
                            showToast("რეგისტრაცია წარმატებით დასრულდა")
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

    private fun navigateToLogin() =
        Navigation.findNavController(requireView()).apply {
            popBackStack()
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
