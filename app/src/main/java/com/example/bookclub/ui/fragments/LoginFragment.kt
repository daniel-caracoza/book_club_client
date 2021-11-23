package com.example.bookclub.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.bookclub.R
import com.example.bookclub.databinding.FragmentLoginBinding
import com.example.bookclub.dependency_injection.RefreshTokenCoroutineWorker
import com.example.bookclub.viewModels.LoginViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val loginViewModel: LoginViewModel by hiltNavGraphViewModels(R.id.nav_graph)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentLoginBinding = DataBindingUtil.inflate(layoutInflater,
            R.layout.fragment_login, container, false)
        binding.viewModel = loginViewModel

        //checks to see if login button is enabled dependent if fields are filled
        loginViewModel.isLoginButtonEnabled.observe(viewLifecycleOwner,{
            it?.let {
                binding.loginButton.isEnabled = it
            }
        })

        binding.signUpButton.setOnClickListener {
            val directions = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            findNavController().navigate(directions)
        }

        //if login is successful, navigate to HomeFragment
        loginViewModel.loginSuccess.observe(viewLifecycleOwner, {
            it?.let {
                if(it){
                    val refreshAccessWorkRequest = PeriodicWorkRequestBuilder<RefreshTokenCoroutineWorker>(15, TimeUnit.MINUTES, 1, TimeUnit.MINUTES)
                        .setInitialDelay(15, TimeUnit.MINUTES)
                        .build()
                    WorkManager.getInstance(requireContext()).enqueueUniquePeriodicWork(
                        "refreshTokenWorkRequest",
                        ExistingPeriodicWorkPolicy.REPLACE,
                        refreshAccessWorkRequest
                    )
                    val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
                    findNavController().navigate(action)
                }
            }
        })
        //display Snackbar if login fails
        loginViewModel.loginFailed.observe(viewLifecycleOwner, {
            it?.let {
                if(it){
                    Snackbar.make(requireView(), getString(R.string.failed_login), Snackbar.LENGTH_LONG).show()
                }
            }
        })
        binding.usernameEditText.doAfterTextChanged {
            it?.let {
                loginViewModel.username = it.toString()
                loginViewModel.isFormComplete()
            }
        }

        binding.passwordEditText.doAfterTextChanged {
            it?.let {
                loginViewModel.password = it.toString()
                loginViewModel.isFormComplete()
            }
        }

        return binding.root
    }

}