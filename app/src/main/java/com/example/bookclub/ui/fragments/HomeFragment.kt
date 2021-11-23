package com.example.bookclub.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.example.bookclub.R
import com.example.bookclub.ui.adapters.ReadingListAdapter
import com.example.bookclub.databinding.FragmentHomeBinding
import com.example.bookclub.viewModels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by hiltNavGraphViewModels(R.id.nav_graph)
    //TODO(add to reading list button functionality needs to be implemented)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentHomeBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_home, container,  false)
        val adapter = ReadingListAdapter()
        binding.readingList.adapter = adapter
        viewModel.getUserBooks().observe(viewLifecycleOwner, {
            it?.let {
                when(it.size){
                    0 -> {
                        binding.emptyListLayout.isVisible = true
                    }
                    else -> {
                        adapter.submitList(it)
                    }
                }
            }
        })
        return binding.root
    }

}