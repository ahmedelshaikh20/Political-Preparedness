package com.example.final_capstone.election

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.final_capstone.network.models.Division
import com.example.final_capstone.network.models.Election
import org.json.JSONObject
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*


@RequiresApi(Build.VERSION_CODES.O)
fun parse_elec(jsonResult: JSONObject): ArrayList<Election> {

  val ElectionList = ArrayList<Election>()
  val elecions = jsonResult.getJSONArray("elections")
  Log.i("LOO", elecions.toString())
  var simpleFormat = DateTimeFormatter.ISO_DATE;
  for (i in 0 until elecions.length()) {
    val curr_election = elecions.getJSONObject(i)
    val name = curr_election.getString("name");
    val id = curr_election.getInt("id");
    val electionDay_String = curr_election.getString("electionDay");
    var convertedDate = LocalDate.parse(electionDay_String, simpleFormat)
    val ocdDivision: String = curr_election.getString("ocdDivisionId")
    // val ocdDivision :String =curr_election.getString("ocdDivisionId")

    val ocdDivisionId = 2
    val elecion = Election(
      id, name, convertToDateViaInstant(convertedDate), Division("id", "US", "state")
    )
    ElectionList.add(elecion)

  }

  return ElectionList

}

@RequiresApi(Build.VERSION_CODES.O)
fun convertToDateViaInstant(dateToConvert: LocalDate): Date {
  return Date.from(
    dateToConvert.atStartOfDay()
      .atZone(ZoneId.systemDefault())
      .toInstant()
  )
}
