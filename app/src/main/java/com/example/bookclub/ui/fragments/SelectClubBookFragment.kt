package com.example.bookclub.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.bookclub.R
import com.example.bookclub.databinding.FragmentSelectClubBookBinding
import com.example.bookclub.ui.adapters.SelectClubBookAdapter
import com.example.bookclub.utils.showError
import com.example.bookclub.viewModels.SelectClubBookViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SelectClubBookFragment : Fragment() {

    private val viewModel: SelectClubBookViewModel by hiltNavGraphViewModels(R.id.nav_graph)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentSelectClubBookBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_select_club_book, container, false)
        val adapter = SelectClubBookAdapter(SelectClubBookAdapter.SelectClubBookListener { book ->
            val directions = SelectClubBookFragmentDirections.actionSelectClubBookFragmentToCreateClubFragment(book)
            findNavController().navigate(directions)
        })
        binding.bookList.adapter = adapter
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { uiState ->
                    uiState.onSuccess {
                        adapter.submitList(it)
                    }
                    uiState.onFailure {
                        showError(requireContext(), it)
                    }
                }
            }
        }
        return binding.root
    }

}