package com.example.final_capstone.network.models


import android.os.Parcelable
import com.example.final_capstone.representative.model.Representative
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
data class Office (
  val name: String,
  @Json(name="divisionId") val division:Division,
  @Json(name="officialIndices") val officials: List<Int>
) {
  fun getRepresentatives(officials: List<Official>): List<Representative> {
    return this.officials.map { index ->
      Representative(officials[index], this)
    }
  }
}
