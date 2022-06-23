package com.example.final_capstone.network.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ElectionResponse(
  val elections: List<Election>,
  val kind: String

)
