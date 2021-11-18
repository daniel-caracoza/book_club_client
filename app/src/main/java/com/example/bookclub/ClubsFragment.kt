package com.example.bookclub

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
import com.example.bookclub.databinding.FragmentClubsBinding
import com.example.bookclub.ui.ClubListAdapter
import com.example.bookclub.utils.showError
import com.example.bookclub.viewModels.ClubsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ClubsFragment : Fragment() {

    private val viewModel: ClubsViewModel by hiltNavGraphViewModels(R.id.nav_graph)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding: FragmentClubsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_clubs, container, false)
        val adapter = ClubListAdapter(ClubListAdapter.ClubItemListener {
            TODO("this is where a click of a club leads to topics")
        })

        binding.addClub.setOnClickListener {
            val directions = ClubsFragmentDirections.actionClubsFragmentToSelectClubBookFragment()
            findNavController().navigate(directions)
        }

        binding.clubList.adapter = adapter

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.clubsState.collect { uiState ->
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