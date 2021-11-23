package com.example.bookclub.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bookclub.databinding.ReadingListItemBinding
import com.example.bookclub.models.DatabaseBook

class ReadingListAdapter: ListAdapter<DatabaseBook, ReadingListAdapter.ViewHolder>(
    ReadingListItemDiffCallback()
) {

    class ViewHolder private constructor(private val binding: ReadingListItemBinding): RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ReadingListItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

        fun bind(item: DatabaseBook){
            binding.databaseBook = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

class ReadingListItemDiffCallback: DiffUtil.ItemCallback<DatabaseBook>() {
    override fun areItemsTheSame(oldItem: DatabaseBook, newItem: DatabaseBook): Boolean {
        return oldItem.bookId == newItem.bookId
    }

    override fun areContentsTheSame(oldItem: DatabaseBook, newItem: DatabaseBook): Boolean {
        return oldItem == newItem
    }

}
