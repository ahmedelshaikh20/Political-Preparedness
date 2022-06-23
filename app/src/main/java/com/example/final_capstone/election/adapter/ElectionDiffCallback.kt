package com.example.final_capstone.election.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.final_capstone.network.models.Election

class ElectionDiffCallback : DiffUtil.ItemCallback<Election>() {
  override fun areItemsTheSame(oldItem: Election, newItem: Election): Boolean {
    return oldItem.id == newItem.id
  }

  override fun areContentsTheSame(oldItem: Election, newItem: Election): Boolean {
    return oldItem == newItem
  }

}
