package com.example.final_capstone.representative.representativeAdapter

import androidx.recyclerview.widget.DiffUtil
import com.example.final_capstone.network.models.Election
import com.example.final_capstone.representative.model.Representative

class RepresentativeDiffCallback: DiffUtil.ItemCallback<Representative>() {
  override fun areItemsTheSame(oldItem: Representative, newItem: Representative): Boolean {
    return oldItem.office == newItem.office
  }

  override fun areContentsTheSame(oldItem: Representative, newItem: Representative): Boolean {
    return oldItem == newItem
  }

}
