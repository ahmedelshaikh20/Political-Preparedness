package com.example.final_capstone.election

import android.util.Log
import androidx.lifecycle.*
import com.example.final_capstone.database.ElectionDao
import com.example.final_capstone.network.CivicsApi
import com.example.final_capstone.network.models.Address
import com.example.final_capstone.network.models.AdministrationBody
import com.example.final_capstone.network.models.Division
import com.example.final_capstone.network.models.Election
import kotlinx.coroutines.launch


class VoterInfoViewModel(private val dataSource: ElectionDao) : ViewModel() {


  private val _VoterInfo = MutableLiveData<Election>()
  val VoterInfo: LiveData<Election>
    get() = _VoterInfo

  private val _Followed = MutableLiveData<Boolean>()
  val Followed: LiveData<Boolean>
    get() = _Followed

  private val _url = MutableLiveData<String?>()
  val url: LiveData<String?>
    get() = _url

  private val _administrationBody = MutableLiveData<AdministrationBody?>()
  val administrationBody: LiveData<AdministrationBody?>
    get() = _administrationBody


  init {
  }

  fun setFollow() {
    viewModelScope.launch {
      if (_VoterInfo.value != null)
        _Followed.let {
          if (dataSource.getElection(_VoterInfo.value!!.id) != null)
            _Followed.value = true
        }
    }

  }

  fun UrlCompleted() {
    _url.value = null
  }

  fun getVoterInfo(division: Division, electionId: Int) {

    viewModelScope.launch {

      val address = "${division.state},${division.country}"
      if (division.state != "") {
        val voterinfoResponse = CivicsApi.retrofitService.getVoterInfo(address, electionId)
        if (voterinfoResponse != null)
          _administrationBody.value = voterinfoResponse.state?.first()?.electionAdministrationBody
      }
    }

  }

  fun UpdateVoterInfo(election: Election) {

    _VoterInfo.value = election

    getVoterInfo(election.division, election.id)
  }


  fun goToUrl(url: String) {
    Log.d("LOOL", url)

    _url.value = url
  }


  fun ChangeFollowElection() {
    viewModelScope.launch {
      _VoterInfo.let {
        if (_Followed.value == true) {
          dataSource.deleteElection(it.value)
          _Followed.value = false
        } else {
          dataSource.insertElection(it.value)
          _Followed.value = true
        }
      }
    }
  }


  /**
   * Hint: The saved state can be accomplished in multiple ways. It is directly related to how elections are saved/removed from the database.
   */

}

@Suppress("UNCHECKED_CAST")
class VoterInfoViewModelFactory(private val localDataSource: ElectionDao) :
  ViewModelProvider.NewInstanceFactory() {
  override fun <T : ViewModel> create(modelClass: Class<T>) =
    (VoterInfoViewModel(localDataSource) as T)
}
