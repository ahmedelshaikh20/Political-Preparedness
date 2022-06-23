package com.example.final_capstone.representative

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.example.final_capstone.R
import com.example.final_capstone.database.ElectionDao
import com.example.final_capstone.election.VoterInfoViewModel
import com.example.final_capstone.network.CivicsApi
import com.example.final_capstone.network.CivicsApiService
import com.example.final_capstone.network.models.Address
import com.example.final_capstone.representative.model.Representative
import kotlinx.coroutines.launch

class RepresentativeViewModel(
  private val context: Context,
) : ViewModel() {

  private val addressList = context.resources.getStringArray(R.array.states)
  // live data for representatives and
  private val _representatives = MutableLiveData<List<Representative>>()
  val representatives: LiveData<List<Representative>>
    get() = _representatives

  // live data for address
  private val _address = MutableLiveData<Address>()
  val address: LiveData<Address>
    get() = _address


  private val _addressIndex = MutableLiveData<Int>()
  val addressIndex: LiveData<Int>
    get() = _addressIndex

   var _addressPresent = false

  init {
    _address.value = Address("", "", "", "", "")

  }

  fun setAddress(address: Address) {
    _address.value = address
      _addressPresent = true
    _addressIndex.value = addressList.indexOf(address.state)
  }

  /**
   *  The following code will prove helpful in constructing a representative from the API. This code combines the two nodes of the RepresentativeResponse into a single official :
  val (offices, officials) = getRepresentativesDeferred.await()
  _representatives.value = offices.flatMap { office -> office.getRepresentatives(officials) }
  Note: getRepresentatives in the above code represents the method used to fetch data from the API
  Note: _representatives in the above code represents the established mutable live data housing representatives
   */

  fun getRpresntatives() {
    viewModelScope.launch {

      val response = CivicsApi.retrofitService.getRepresentatives(address.value.toString())

      if(response.isSuccessful){

      _representatives.value = response.body()?.offices?.flatMap { office -> office.getRepresentatives(response.body()!!.officials) }}
      else
      {
      Toast.makeText(context , "Failed to get representatives for that address" , Toast.LENGTH_LONG).show()
      }
    }
  }


}

@Suppress("UNCHECKED_CAST")
class RepresentativeViewModelFactory(private val context: Context) :
  ViewModelProvider.NewInstanceFactory() {
  override fun <T : ViewModel> create(modelClass: Class<T>) =
    (RepresentativeViewModel(context) as T)
}
