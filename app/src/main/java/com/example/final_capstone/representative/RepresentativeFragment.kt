package com.example.final_capstone.representative


import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.view.isNotEmpty
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.final_capstone.BuildConfig
import com.example.final_capstone.R
import com.example.final_capstone.database.ElectionDatabase
import com.example.final_capstone.databinding.FragmentRepresentaiveBinding
import com.example.final_capstone.databinding.FragmentVoterInfoBinding
import com.example.final_capstone.election.ElectionViewModelFactory
import com.example.final_capstone.election.ElectionsViewModel
import com.example.final_capstone.election.VoterInfoViewModel
import com.example.final_capstone.election.VoterInfoViewModelFactory
import com.example.final_capstone.election.adapter.ElectionListAdapter
import com.example.final_capstone.network.models.Address
import com.example.final_capstone.representative.representativeAdapter.RepresentativeListAdapter
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import java.util.Locale

class RepresentativeFragment : Fragment() {
  private lateinit var binding : FragmentRepresentaiveBinding
  private val viewModel :RepresentativeViewModel by viewModels<RepresentativeViewModel> {
    RepresentativeViewModelFactory(requireContext())
  }
  companion object {
    val REQUEST_FOREGROUND_ONLY_PERMISSIONS_REQUEST_CODE =1

  }
  lateinit var Representative_adapter: RepresentativeListAdapter

  private lateinit var fusedLocationClient: FusedLocationProviderClient


  override fun onCreateView(inflater: LayoutInflater,
                            container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {

binding = FragmentRepresentaiveBinding.inflate(inflater)

    Representative_adapter = RepresentativeListAdapter()
    binding.representativesRecyclerView.adapter = Representative_adapter
viewModel.representatives.observe(viewLifecycleOwner){
  Representative_adapter.submitList(it)
}
    viewModel.address.observe(viewLifecycleOwner){
      binding.address = it
    }
    binding.buttonSearch.setOnClickListener {
      hideKeyboard()
      if(!addressisAvailabe())
      viewModel.getRpresntatives()
      else
        Toast.makeText(requireContext() , "Address is Needed" , Toast.LENGTH_SHORT).show()
    }
    binding.buttonLocation.setOnClickListener {
      getCurrentLocation()
    }
    fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

    return binding.root
  }

  private fun addressisAvailabe(): Boolean {


    if(binding.addressLine1.text.isNullOrEmpty()||binding.city.text.isNullOrEmpty()||binding.zip.text.isNullOrEmpty()&&binding.state.isNotEmpty())
    {
      val address  = Address(binding.addressLine1.text.toString() ,binding.addressLine2.text.toString(),binding.city.text.toString(),binding.state.toString() ,binding.zip.text.toString() )
      viewModel.setAddress(address)
    }

return (binding.addressLine1.text.isNullOrEmpty()||binding.city.text.isNullOrEmpty()||binding.zip.text.isNullOrEmpty()&&binding.state.isNotEmpty())
  }

  override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
    if(grantResults.isEmpty() ||
        grantResults[0] == PackageManager.PERMISSION_DENIED){
      Snackbar.make(
        binding.root,
        R.string.permission_denied_explanation,
        Snackbar.LENGTH_INDEFINITE
      )
        .setAction(R.string.settings) {
          startActivity(Intent().apply {
            action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            data = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
          })
        }.show()
    }else
      getCurrentLocation()
  }

  private fun checkLocationPermissions() {
     if (isPermissionGranted()) {
      getCurrentLocation()
    } else {
      var permissionsArray = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
      val resultCode = REQUEST_FOREGROUND_ONLY_PERMISSIONS_REQUEST_CODE
      ActivityCompat.requestPermissions(
        requireActivity(),
        permissionsArray,
        resultCode
      )

    }
  }

  @SuppressLint("MissingPermission")
  private fun getCurrentLocation() {
    if (isPermissionGranted()){
    fusedLocationClient.lastLocation
      .addOnSuccessListener { location : Location? ->
        // Got last known location. In some rare situations this can be null.
        location?.let {
        //let  allows u to use copy of the location  (as it can change anytime )
          Log.d("loo" , it.toString())

          val address = geoCodeLocation(location)
viewModel.setAddress(address)
        }

      }
      .addOnFailureListener {
        Log.e("Reprsenatvie Fragment" , it.toString())
      }}else
        checkLocationPermissions()
  }

  private fun isPermissionGranted() : Boolean {
    // We Only need the foreground Permission
    val foregroundLocationApproved = (
      PackageManager.PERMISSION_GRANTED ==
        ActivityCompat.checkSelfPermission(requireActivity(),
          Manifest.permission.ACCESS_FINE_LOCATION))


    return foregroundLocationApproved
   }



  private fun geoCodeLocation(location: Location): Address {
    val geocoder = Geocoder(context, Locale.getDefault())
    return geocoder.getFromLocation(location.latitude, location.longitude, 1)
      .map { address ->
        Address(
          address.thoroughfare+"",
          address.subThoroughfare,
          address.locality,
          address.adminArea,
          address.postalCode
        )
      }
      .first()
  }
  private fun hideKeyboard() {
    val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(requireView().windowToken, 0)
  }

}
