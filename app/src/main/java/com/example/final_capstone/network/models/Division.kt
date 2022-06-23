package com.example.final_capstone.network.models


import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Division(
  val id: String,
  val country: String,
  val state: String
): Parcelable
