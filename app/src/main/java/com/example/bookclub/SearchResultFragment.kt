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
import com.example.bookclub.databinding.FragmentSearchResultBinding
import com.example.bookclub.viewModels.SearchResultViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchResultFragment : Fragment() {

    private val arguments: SearchResultFragmentArgs by navArgs()
    private val viewModel: SearchResultViewModel by hiltNavGraphViewModels(R.id.nav_graph)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentSearchResultBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_search_result, container, false)
        val adapter = SearchResultAdapter(SearchResultAdapter.SearchResultItemListener { isbn: String ->
            val directions = SearchResultFragmentDirections.actionSearchResultFragmentToSearchBookFragment(isbn)
            findNavController().navigate(directions)
        })
        binding.searchResultList.adapter = adapter
        viewModel.searchResults(arguments.searchTerm, "books").observe(viewLifecycleOwner, {
            it?.let {
                adapter.submitList(it)
            }
        })
        return binding.root
    }

}