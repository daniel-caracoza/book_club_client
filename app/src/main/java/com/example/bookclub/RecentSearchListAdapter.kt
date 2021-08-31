package com.example.bookclub

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bookclub.databinding.RecentSearchItemBinding
import com.example.bookclub.models.SearchItem

class RecentSearchListAdapter: ListAdapter<SearchItem, RecentSearchListAdapter.ViewHolder>(RecentSearchItemDiffCallback()) {

    class ViewHolder private constructor(val binding: RecentSearchItemBinding): RecyclerView.ViewHolder(binding.root){

        companion object {

            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RecentSearchItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

        fun bind(searchItem: SearchItem){
            binding.searchItem = searchItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class RecentSearchItemDiffCallback: DiffUtil.ItemCallback<SearchItem>(){
    override fun areItemsTheSame(oldItem: SearchItem, newItem: SearchItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: SearchItem, newItem: SearchItem): Boolean {
        return newItem == oldItem
    }

}