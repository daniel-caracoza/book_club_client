package com.example.bookclub.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bookclub.databinding.ClubListItemBinding
import com.example.bookclub.models.Club

class ClubListAdapter(val listener: ClubItemListener): ListAdapter<Club, ClubListAdapter.ViewHolder>(ClubItemDiffCallback()) {

    class ViewHolder private constructor(val binding: ClubListItemBinding): RecyclerView.ViewHolder(binding.root) {

        companion object {

            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ClubListItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

        fun bind(item: Club, listener: ClubItemListener){
            binding.club = item
            binding.listener = listener
        }
    }

    class ClubItemListener(val listener: (club: Club) -> Unit) {
        fun onClick(club: Club) = listener(club)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder.from(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), listener)
    }
}

class ClubItemDiffCallback(): DiffUtil.ItemCallback<Club>() {
    override fun areItemsTheSame(oldItem: Club, newItem: Club): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Club, newItem: Club): Boolean = oldItem == newItem

}