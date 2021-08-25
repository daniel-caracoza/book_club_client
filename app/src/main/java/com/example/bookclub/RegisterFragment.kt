package com.example.bookclub

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.bookclub.databinding.FragmentRegisterBinding
import com.example.bookclub.viewModels.RegisterViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint()
class RegisterFragment : Fragment() {

    private val viewModel: RegisterViewModel by hiltNavGraphViewModels(R.id.nav_graph)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding: FragmentRegisterBinding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_register, container, false)
        binding.viewModel = viewModel

        binding.usernameEditText.doAfterTextChanged {
            viewModel.username = it.toString()
            viewModel.isFormComplete()
        }

        binding.emailEditText.doAfterTextChanged {
            viewModel.email = it.toString()
            viewModel.isFormComplete()
        }

        binding.passwordEditText.doAfterTextChanged {
            viewModel.password = it.toString()
            viewModel.isFormComplete()
        }

        viewModel.isFormComplete.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.registerButton.isEnabled = it
            }
        })
        viewModel.registerSuccess.observe(viewLifecycleOwner, {
            it?.let {
                Toast.makeText(requireContext(), getString(R.string.register_success), Toast.LENGTH_SHORT).show()
                // ??
                findNavController().popBackStack()
            }
        })

        return binding.root
    }
}