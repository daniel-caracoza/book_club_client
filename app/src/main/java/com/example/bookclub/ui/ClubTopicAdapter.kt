package com.example.bookclub.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bookclub.databinding.ClubtopicListItemBinding
import com.example.bookclub.models.ClubTopic

class ClubTopicAdapter(val listener: ClubTopicClickListener): ListAdapter<ClubTopic, ClubTopicAdapter.ViewHolder>(ClubTopicDiffCallback()) {

    class ViewHolder private constructor(val binding: ClubtopicListItemBinding): RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ClubtopicListItemBinding.inflate(inflater, parent, false)
                return ViewHolder(binding)
            }
        }

        fun bind(item: ClubTopic, listener: ClubTopicClickListener){
            binding.clubTopic = item
            binding.listener = listener
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), listener)
    }

    class ClubTopicClickListener(val listener: (topic: ClubTopic) -> Unit) {
         fun onClick(topic: ClubTopic) = listener(topic)
    }

}

class ClubTopicDiffCallback: DiffUtil.ItemCallback<ClubTopic>() {

    override fun areItemsTheSame(oldItem: ClubTopic, newItem: ClubTopic): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: ClubTopic, newItem: ClubTopic): Boolean =
        oldItem == newItem

}