package com.example.contactapptoken.presentation.screen.verify

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.contactapptoken.R
import com.example.contactapptoken.data.source.remote.request.VerifyCodeRequest
import com.example.contactapptoken.databinding.ScreenCodeVerifyBinding
import com.example.contactapptoken.presentation.screen.verify.viewmodel.VerifyRepository
import com.example.contactapptoken.presentation.screen.verify.viewmodel.impl.VerifyRepositoryImpl
import com.example.contactapptoken.utils.myApply
import kotlinx.coroutines.launch

class VerifyFragment : Fragment(R.layout.screen_code_verify) {
    private val binding by viewBinding(ScreenCodeVerifyBinding::bind)
    private val viewModel: VerifyRepository by viewModels<VerifyRepositoryImpl>()
    private val code = StringBuilder()
    private val args: VerifyFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        viewModel.openRegisterScreenLiveData.observe(this, openRegisterScreenObserver)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.myApply {

        progressBar.hide()

        viewModel.progressLoadingLivedata.observe(viewLifecycleOwner, progressBarObserver)
        viewModel.openHomeScreenLiveData.observe(viewLifecycleOwner, openHomeScreenObserver)
        viewModel.errorLiveData.observe(viewLifecycleOwner, errorMessage)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.openRegisterScreenLiveData.collect {
                findNavController().navigate(R.id.action_verifyFragment_to_registerFragment)
            }
        }

        binding.keyboard.apply {
            btOne.setOnClickListener {
                code.append("1")
                inputSmsCode.setText(code)
            }

            btTwo.setOnClickListener {
                code.append("2")
                inputSmsCode.setText(code)
            }

            btThree.setOnClickListener {
                code.append("3")
                inputSmsCode.setText(code)
            }

            btFour.setOnClickListener {
                code.append("4")
                inputSmsCode.setText(code)
            }

            btFive.setOnClickListener {
                code.append("5")
                inputSmsCode.setText(code)
            }

            btSix.setOnClickListener {
                code.append("6")
                inputSmsCode.setText(code)
            }

            btSeven.setOnClickListener {
                code.append("7")
                inputSmsCode.setText(code)
            }

            btEight.setOnClickListener {
                code.append("8")
                inputSmsCode.setText(code)
            }

            btNine.setOnClickListener {
                code.append("9")
                inputSmsCode.setText(code)
            }

            btZero.setOnClickListener {
                code.append("0")
                inputSmsCode.setText(code)
            }

            btClear.setOnClickListener {
                if (code.isNotEmpty()) {
                    code.deleteCharAt(code.length - 1)
                    inputSmsCode.setText(code)
                }
            }

            btConfirm.setOnClickListener {
                if (code.length == 6) {
                    val phoneNumber = args.phone
                    Log.d("DDD", "TEL NUMBER -> $phoneNumber")
                    val verify = VerifyCodeRequest(phone = phoneNumber, code = code.toString())
                    viewModel.clickSubmit(verify)

                }
            }
        }

        backBtn.setOnClickListener {
            viewModel.openRegisterScreen()
        }
    }

    // Observer

    private val progressBarObserver = Observer<Boolean> {
        if (it) {
            binding.progressBar.show()
        } else {
            binding.progressBar.hide()
        }
    }

    private val openHomeScreenObserver = Observer<Unit> {
        findNavController().navigate(R.id.action_verifyFragment_to_homeScreen)
    }

    private val openRegisterScreenObserver = Observer<Unit> {
        findNavController().navigate(R.id.action_verifyFragment_to_registerFragment)
    }

    private val errorMessage = Observer<String> {
        Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT).show()
    }
}