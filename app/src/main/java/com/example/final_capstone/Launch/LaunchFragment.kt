package com.example.final_capstone.Launch

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.final_capstone.R
import com.example.final_capstone.databinding.FragmentLaunchBinding
import com.example.final_capstone.network.CivicsApi


class LaunchFragment : Fragment() {

  private lateinit var binding: FragmentLaunchBinding
  private val navController by lazy { findNavController() }


  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    binding = FragmentLaunchBinding.inflate(inflater)
    binding.upcomingButton.setOnClickListener {
      navController.navigate(LaunchFragmentDirections.actionLaunchFragmentToElectionFragment());
    }
    binding.representativeButton.setOnClickListener {
      navController.navigate(LaunchFragmentDirections.actionLaunchFragmentToRepresentativeFragment());
    }
    return binding.root
  }


}
