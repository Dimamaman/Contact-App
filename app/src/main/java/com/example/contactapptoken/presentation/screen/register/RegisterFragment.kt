package com.example.contactapptoken.presentation.screen.register

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.contactapptoken.R
import com.example.contactapptoken.data.source.local.MySharedPref
import com.example.contactapptoken.data.source.remote.request.RegisterRequest
import com.example.contactapptoken.databinding.ScreenRegisterBinding
import com.example.contactapptoken.presentation.screen.register.viewmodel.RegisterRepository
import com.example.contactapptoken.presentation.screen.register.viewmodel.impl.RegisterRepositoryImpl
import com.example.contactapptoken.utils.myApply

class RegisterFragment : Fragment(R.layout.screen_register) {
    private val binding by viewBinding(ScreenRegisterBinding::bind)
    private val viewModel: RegisterRepository by viewModels<RegisterRepositoryImpl>()
    private var phoneNumber = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.myApply {
        progress.hide()

        viewModel.sendMessage.observe(viewLifecycleOwner, message)
        viewModel.openVerifyScreenLiveData.observe(viewLifecycleOwner, openVerifyScreenObserver)
        viewModel.progressLoadingLivedata.observe(viewLifecycleOwner, progressObserver)
        viewModel.openLoginScreenLiveData.observe(viewLifecycleOwner,openLoginScreenObserver)

        buttonRegister.setOnClickListener {

            if (inputFirstName.text.toString().isEmpty() ||
                inputLastName.text.toString().isEmpty() ||
                inputPassword.text.toString().isEmpty() ||
                inputConfirmPassword.text.toString().isEmpty()
            ) {

                Toast.makeText(requireContext(), "Fieldlarni to'ldiring", Toast.LENGTH_SHORT).show()
            } else if (inputPassword.text.toString() != inputConfirmPassword.text.toString()) {

                Toast.makeText(requireContext(), "Password mos emas", Toast.LENGTH_SHORT).show()
            } else {
                val firstName = inputFirstName.text.toString()
                val lastName = inputLastName.text.toString()
                val password = inputPassword.text.toString()
                val phone = inputPhoneNumber.text.toString()
                phoneNumber = phone
                MySharedPref.getInstance().parol = password
                val register = RegisterRequest(firstName, lastName, password, phone)
                viewModel.register(register)
            }
        }

        backBtn.setOnClickListener {
            viewModel.openLoginScreen()
        }

    }

    // Observers
    private val message = Observer<String> {

        Toast.makeText(requireContext(), "Send Code", Toast.LENGTH_SHORT).show()
    }

    private val progressObserver = Observer<Boolean> {
        if (it) {
            binding.progress.show()
        } else {
            binding.progress.hide()
        }
    }


    private val openVerifyScreenObserver = Observer<Unit> {
        val action = RegisterFragmentDirections.actionRegisterFragmentToVerifyFragment(phoneNumber)
        findNavController().navigate(action)
    }

    private val openLoginScreenObserver = Observer<Unit> {
        findNavController().navigateUp()
    }
}