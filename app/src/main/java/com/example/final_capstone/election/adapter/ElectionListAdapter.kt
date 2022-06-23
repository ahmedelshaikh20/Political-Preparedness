package com.example.final_capstone.election.adapter


import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.final_capstone.network.models.Election

class ElectionListAdapter(private val itemClickListener: ElectionListener) :
  ListAdapter<Election, ElectionViewHolder>(ElectionDiffCallback()) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElectionViewHolder {
    return ElectionViewHolder.create(parent)
  }

  override fun onBindViewHolder(holder: ElectionViewHolder, position: Int) {
    val election = getItem(position);

    holder.bind(election, itemClickListener);
  }

}


