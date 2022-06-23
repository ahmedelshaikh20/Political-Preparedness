package com.example.final_capstone.election

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.example.final_capstone.database.ElectionDao
import com.example.final_capstone.network.CivicsApi
import com.example.final_capstone.network.jsonadapters.divisionFromJson
import com.example.final_capstone.network.models.Division
import com.example.final_capstone.network.models.Election
import org.json.JSONObject

import kotlinx.coroutines.launch
import java.util.*

//TOD: Construct ViewModel and provide election datasource
@RequiresApi(Build.VERSION_CODES.O)
class ElectionsViewModel(val electionDao: ElectionDao) : ViewModel() {
  private val _upcomingelections = MutableLiveData<List<Election>>()
  val upcomingelections: LiveData<List<Election>>
    get() = _upcomingelections


  private val _savedelections = MutableLiveData<List<Election>>()
  val savedelections: LiveData<List<Election>>
    get() = _savedelections

  private val _GoToElectionDetaits = MutableLiveData<Election?>()
  val GoToElectionDetaits :LiveData<Election?>
  get() = _GoToElectionDetaits

  private val _SelectedElection = MutableLiveData<Boolean>()
  val SelectedElection :LiveData<Boolean>
  get() = _SelectedElection



  init {
    viewModelScope.launch {
      getUpcomingElections()
      getSavedElectionsFromDatabase()
    }
  }

   fun UpdateData(){
    viewModelScope.launch {
    getSavedElectionsFromDatabase()}
  }

  private suspend fun getSavedElectionsFromDatabase() {

    _savedelections.value=electionDao.getAllSavedElections()
  }


  @RequiresApi(Build.VERSION_CODES.O)
  private suspend fun getUpcomingElections() {
    var Election_Response = CivicsApi.retrofitService.getElections();
    val Str_electionResponse = CivicsApi.retrofitService.getElectionsStr()
    val elecions = JSONObject(Str_electionResponse.string()).getJSONArray("elections")
    for (i in 0 until elecions.length()) {
      val curr_election = elecions.getJSONObject(i)
      val division_Str= curr_election.getString("ocdDivisionId")
      val _Division = divisionFromJson(division_Str)
      Election_Response.elections.get(i).division = _Division
    }
    _upcomingelections.value = Election_Response.elections

  }
  fun displayElection(election: Election) {
    _GoToElectionDetaits.value = election
  }

  fun disableGotoElections(){
    _GoToElectionDetaits.value = null

  }










}
@Suppress("UNCHECKED_CAST")
class ElectionViewModelFactory (private val localDataSource: ElectionDao) : ViewModelProvider.NewInstanceFactory() {
  @RequiresApi(Build.VERSION_CODES.O)
  override fun <T : ViewModel> create(modelClass: Class<T>) =
    (ElectionsViewModel(localDataSource) as T)
}

