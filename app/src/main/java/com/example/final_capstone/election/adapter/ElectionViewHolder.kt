package com.example.final_capstone.election.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.final_capstone.databinding.ElectionListItemBinding
import com.example.final_capstone.network.models.Election

class ElectionViewHolder constructor(private val binding: ElectionListItemBinding) :
  RecyclerView.ViewHolder(binding.root) {

  fun bind(item: Election, ClickitemListener: ElectionListener) {
    Log.i("LOOL", item.toString())
    binding.election = item
    binding.listener = ClickitemListener
    binding.executePendingBindings()
  }

  companion object {
    fun create(parent: ViewGroup): ElectionViewHolder {
      val layoutInflater = LayoutInflater.from(parent.context)
      val binding = ElectionListItemBinding.inflate(layoutInflater, parent, false)
      return ElectionViewHolder(binding)
    }
  }
}
