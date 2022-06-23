package com.example.final_capstone.network.jsonadapters

import android.util.Log
import com.example.final_capstone.network.models.Division

  fun divisionFromJson (ocdDivisionId: String): Division {
    val countryDelimiter = "country:"
    val stateDelimiter = "state:"
    val country = ocdDivisionId.substringAfter(countryDelimiter,"")
      .substringBefore("/")
    val state = ocdDivisionId.substringAfter(stateDelimiter,"")
      .substringBefore("/")

    Log.i("LOO" , state)
    return Division(ocdDivisionId, country, state)
  }

