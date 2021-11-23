package com.example.bookclub.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bookclub.databinding.SelectClubBookItemBinding
import com.example.bookclub.models.DatabaseBook


class SelectClubBookAdapter(val listener: SelectClubBookListener): ListAdapter<DatabaseBook, SelectClubBookAdapter.ViewHolder>(
    ReadingListItemDiffCallback()
) {

    class ViewHolder private constructor(private val binding: SelectClubBookItemBinding): RecyclerView.ViewHolder(binding.root){

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = SelectClubBookItemBinding.inflate(inflater, parent, false)
                return ViewHolder(binding)
            }
        }

        fun bind(item: DatabaseBook, listener: SelectClubBookListener){
            binding.databaseBook = item
            binding.listener = listener
        }

    }

    class SelectClubBookListener(val listener: (item: DatabaseBook) -> Unit){
        fun onClick(item: DatabaseBook) = listener(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), listener)
    }
}
