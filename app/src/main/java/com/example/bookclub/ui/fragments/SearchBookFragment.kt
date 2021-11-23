package com.example.bookclub.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.navArgs
import com.example.bookclub.R
import com.example.bookclub.databinding.FragmentSearchBookBinding
import com.example.bookclub.viewModels.SearchBookViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchBookFragment : Fragment() {

    private val TAG = "SearchBookFragment"

    private val args: SearchBookFragmentArgs by navArgs()
    private val viewModel: SearchBookViewModel by hiltNavGraphViewModels(R.id.nav_graph)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentSearchBookBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_search_book, container, false)
        viewModel.findByISBN(args.isbn).observe(viewLifecycleOwner, {
            it?.let {
                binding.searchBook = it
            }
        })
        viewModel.isBookInReadingList(args.isbn)
        viewModel.addToListIsEnabled.observe(viewLifecycleOwner, {
            it?.let {
                binding.addToListButton.isEnabled = it
            }
        })

        binding.addToListButton.setOnClickListener {
            viewModel.disableAddToListButton()
            viewModel.addToReadingList(binding.searchBook!!).observe(viewLifecycleOwner, { isSuccessful ->
                isSuccessful?.let {
                    if(!isSuccessful){
                        Snackbar.make(requireView(), getString(R.string.failed_add_to_reading_list), Snackbar.LENGTH_SHORT).show()
                    }
                    Snackbar.make(requireView(), getString(R.string.success_add_to_reading_list), Snackbar.LENGTH_SHORT).show()
                }
            })
        }

        return binding.root
    }
}