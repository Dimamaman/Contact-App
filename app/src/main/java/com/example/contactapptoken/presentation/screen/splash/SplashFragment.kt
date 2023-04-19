package com.example.contactapptoken.presentation.screen.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.contactapptoken.R
import com.example.contactapptoken.databinding.ScreenSplashBinding
import com.example.contactapptoken.presentation.screen.splash.viewmodel.SplashViewModel
import com.example.contactapptoken.presentation.screen.splash.viewmodel.impl.SplashViewModelImpl

class SplashFragment : Fragment(R.layout.screen_splash) {
    private val binding by viewBinding(ScreenSplashBinding::bind)
    private val viewModel: SplashViewModel by viewModels<SplashViewModelImpl>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        viewModel.findScreen()

        viewModel.openHomeScreenLiveData.observe(viewLifecycleOwner, homeObserver)
        viewModel.openLoginScreenLiveData.observe(viewLifecycleOwner, loginObserver)
    }

    private val homeObserver = Observer<Unit> {
        findNavController().navigate(R.id.action_splashFragment_to_homeScreen)
    }

    private val loginObserver = Observer<Unit> {
        findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
    }
}