package com.example.bookclub

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bookclub.databinding.SearchResultItemBinding
import com.example.bookclub.models.SearchResultItem

class SearchResultAdapter(val listener: SearchResultItemListener): PagingDataAdapter<SearchResultItem, SearchResultAdapter.ViewHolder>(SearchResultItemDiffCallback()) {

    class ViewHolder private constructor(val binding: SearchResultItemBinding): RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = SearchResultItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

        fun bind(item: SearchResultItem, listener: SearchResultItemListener) {
            binding.clickListener = listener
            binding.searchResultItem = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!, listener)
    }

    class SearchResultItemListener(val listener: (isbn: String) -> Unit){

        fun onClick(searchResultItem: SearchResultItem) = listener(searchResultItem.isbn13)
    }
}

class SearchResultItemDiffCallback: DiffUtil.ItemCallback<SearchResultItem>(){
    override fun areItemsTheSame(oldItem: SearchResultItem, newItem: SearchResultItem): Boolean {
        return oldItem.isbn13 == newItem.isbn13
    }

    override fun areContentsTheSame(oldItem: SearchResultItem, newItem: SearchResultItem): Boolean {
        return oldItem == newItem
    }
}
