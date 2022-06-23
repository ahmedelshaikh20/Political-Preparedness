package com.example.final_capstone.election.adapter

import com.example.final_capstone.network.models.Election

class ElectionListener(private val clickListener: (Election) -> Unit) {
  fun onClick(data: Election) = clickListener(data)
}
