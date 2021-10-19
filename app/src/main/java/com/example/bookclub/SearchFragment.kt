package com.example.bookclub

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.bookclub.databinding.FragmentSearchBinding
import com.example.bookclub.models.SearchItem
import com.example.bookclub.viewModels.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(), RecentSearchListAdapter.RecentSearchItemListener {

    val viewModel: SearchViewModel by hiltNavGraphViewModels(R.id.nav_graph)
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        navController = findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding: FragmentSearchBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        val adapter = RecentSearchListAdapter(this)
        binding.recentSearchList.adapter = adapter
        viewModel.recentSearchItems.observe(viewLifecycleOwner, {
            it?.let {
                adapter.submitList(it.userSearchItems)
            }
        })
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.options_menu, menu)
        val searchView = menu.findItem(R.id.search)
        searchView.expandActionView()
        // Associate searchable configuration with the SearchView
        val searchManager = requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.search).actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
            setOnQueryTextListener(object: SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let{
                        viewModel.addUserRecentSearch(query)
                        navigateToSearchResult(query)
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return true
                }

            })
        }
        super.onCreateOptionsMenu(menu, inflater)

    }

    private fun navigateToSearchResult(searchTerm: String){
        val directions = SearchFragmentDirections.actionSearchFragmentToSearchResultFragment(searchTerm)
        navController.navigate(directions)
    }

    override fun onClick(searchItem: SearchItem) {
        navigateToSearchResult(searchItem.searchTerm)
    }

    override fun onDelete(searchItem: SearchItem) {
        viewModel.deleteSearchItem(searchItem)
    }

}