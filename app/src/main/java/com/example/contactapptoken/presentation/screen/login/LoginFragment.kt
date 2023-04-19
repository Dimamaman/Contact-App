package com.example.contactapptoken.presentation.screen.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.contactapptoken.R
import com.example.contactapptoken.data.source.local.MySharedPref
import com.example.contactapptoken.data.source.remote.request.LoginRequest
import com.example.contactapptoken.databinding.ScreenLoginBinding
import com.example.contactapptoken.presentation.screen.login.viewmodel.LoginViewModel
import com.example.contactapptoken.presentation.screen.login.viewmodel.LoginViewModelImpl
import com.example.contactapptoken.utils.myApply

class LoginFragment : Fragment(R.layout.screen_login) {

    private val binding by viewBinding(ScreenLoginBinding::bind)
    private val viewModel: LoginViewModel by viewModels<LoginViewModelImpl>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.openRegisterScreenLiveData.observe(this, openRegisterScreenObserver)
        viewModel.openHomeScreenLiveData.observe(this, openHomeScreenObserver)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.myApply {

        buttonRegister.setOnClickListener {
            viewModel.openRegisterScreen()
        }

        buttonLogin.setOnClickListener {
            if (inputPassword.length() < 6) {
                return@setOnClickListener
            } else if (inputPhoneNumber.length() != 13 || !inputPhoneNumber.text.startsWith("+998")) {
                return@setOnClickListener
            }

            viewModel.loginSubmit(LoginRequest(inputPhoneNumber.text.toString(),inputPassword.text.toString()))
            MySharedPref.getInstance().parol = inputPassword.text.toString()
        }

        inputPassword.doAfterTextChanged {

        }

        inputPhoneNumber.doAfterTextChanged {

        }

        // LIVEDATA OBSERVE

        viewModel.progressLoadingLivedata.observe(viewLifecycleOwner, progressObserver)
        viewModel.errorLiveData.observe(viewLifecycleOwner, errorObserver)

        viewModel.networkUnAvailableLiveData.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "No Internet", Toast.LENGTH_SHORT).show()
        }
    }

    // OBSERVER
    private val errorObserver = Observer<String> {
        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
    }

    private val openRegisterScreenObserver = Observer<Unit> {
        findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        Toast.makeText(requireContext(), "Register open", Toast.LENGTH_SHORT).show()
    }

    private val progressObserver = Observer<Boolean> {
        if (it) {
            binding.progress.show()
        } else {
            binding.progress.hide()
        }
    }

    private val openHomeScreenObserver = Observer<Unit> {
        findNavController().navigate(R.id.action_loginFragment_to_homeScreen)
        Toast.makeText(requireContext(), "Home Screen open", Toast.LENGTH_SHORT).show()
    }
}