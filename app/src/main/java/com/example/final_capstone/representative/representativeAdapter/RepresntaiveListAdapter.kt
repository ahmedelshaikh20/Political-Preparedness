package com.example.final_capstone.representative.representativeAdapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.final_capstone.representative.model.Representative

class RepresentativeListAdapter : ListAdapter<Representative, RepresentativeViewHolder>(RepresentativeDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepresentativeViewHolder {
      return RepresentativeViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RepresentativeViewHolder, position: Int) {
      val representative = getItem(position);

      holder.bind(representative);
    }




}
