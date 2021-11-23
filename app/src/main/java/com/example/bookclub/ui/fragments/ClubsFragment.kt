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
import com.example.bookclub.databinding.FragmentClubsBinding
import com.example.bookclub.ui.adapters.ClubListAdapter
import com.example.bookclub.utils.showError
import com.example.bookclub.viewModels.ClubsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ClubsFragment : Fragment() {

    private val viewModel: ClubsViewModel by hiltNavGraphViewModels(R.id.nav_graph)
    private lateinit var adapter: ClubListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding: FragmentClubsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_clubs, container, false)
        adapter = ClubListAdapter(ClubListAdapter.ClubItemListener {
            val directions = ClubsFragmentDirections.actionClubsFragmentToClubTopicsFragment(it)
            findNavController().navigate(directions)
        })

        binding.addClub.setOnClickListener {
            val directions = ClubsFragmentDirections.actionClubsFragmentToSelectClubBookFragment()
            findNavController().navigate(directions)
        }
        binding.clubList.adapter = adapter
        getUserClubs()
        return binding.root
    }

    private fun getUserClubs() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.refreshUserClubs().collect { uiState ->
                    uiState.onSuccess {
                        adapter.submitList(it)
                    }
                    uiState.onFailure {
                        showError(requireContext(), it)
                    }
                }
            }
        }
    }
}