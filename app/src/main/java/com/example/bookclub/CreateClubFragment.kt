package com.example.bookclub

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.bookclub.databinding.FragmentCreateClubBinding
import com.example.bookclub.utils.showError
import com.example.bookclub.viewModels.CreateClubViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CreateClubFragment : Fragment() {

    private val args: CreateClubFragmentArgs by navArgs()
    private val vieWModel: CreateClubViewModel by hiltNavGraphViewModels(R.id.nav_graph)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentCreateClubBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_club, container, false)
        binding.book = args.book
        binding.createClub.setOnClickListener { button ->
            button.isEnabled = false
            val clubName = binding.clubNameEditText.text.toString()
            val bookId = args.book.bookId.toString()
            vieWModel.onCreateClubClick(bookId, clubName).observe(viewLifecycleOwner, { uiState ->
                    uiState.onSuccess {
                        findNavController().popBackStack(R.id.clubsFragment, false)
                    }
                    uiState.onFailure {
                        showError(requireContext(), it)
                        button.isEnabled = true
                    }
                })
        }
        return binding.root
    }
}