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
import androidx.navigation.fragment.navArgs
import com.example.bookclub.R
import com.example.bookclub.databinding.FragmentClubTopicsBinding
import com.example.bookclub.ui.adapters.ClubTopicAdapter
import com.example.bookclub.utils.showError
import com.example.bookclub.viewModels.ClubTopicsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ClubTopicsFragment : Fragment() {

    private val viewModel: ClubTopicsViewModel by hiltNavGraphViewModels(R.id.nav_graph)

    private val args: ClubTopicsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentClubTopicsBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_club_topics, container, false)
        binding.club = args.club
        val adapter = ClubTopicAdapter(ClubTopicAdapter.ClubTopicClickListener {
            //TODO()
        })
        binding.clubTopicsList.adapter = adapter
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.getTopics(args.club.id).collect { uiState ->
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