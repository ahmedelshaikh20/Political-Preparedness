package com.example.final_capstone.election

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.final_capstone.database.ElectionDatabase
import com.example.final_capstone.databinding.FragmentElectionBinding
import com.example.final_capstone.election.adapter.ElectionListAdapter
import com.example.final_capstone.election.adapter.ElectionListener

class ElectionFragment : Fragment() {
  private val navController by lazy { findNavController() }

  lateinit var binding: FragmentElectionBinding
  lateinit var upcoming_adapter: ElectionListAdapter
  lateinit var saved_adpter: ElectionListAdapter


  private val viewModel by viewModels<ElectionsViewModel>{
    ElectionViewModelFactory(ElectionDatabase.getInstance(requireContext()).electionDao)
  };
  @RequiresApi(Build.VERSION_CODES.O)
  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    binding = FragmentElectionBinding.inflate(inflater)
    binding.viewModel = viewModel


    upcoming_adapter = ElectionListAdapter(ElectionListener {
      viewModel.displayElection(it)
    })
    saved_adpter = ElectionListAdapter(ElectionListener {
      viewModel.displayElection(it)
    })

    viewModel.GoToElectionDetaits.observe(viewLifecycleOwner){
      if(it!=null){
      navController.navigate(ElectionFragmentDirections.actionElectionFragmentToVoterInfoFragment(it))
      viewModel.disableGotoElections()}
    }
    binding.UpcomingElectionRecycler.adapter = upcoming_adapter
    binding.SavedElectionRecycler.adapter=saved_adpter
    viewModel.upcomingelections.observe(viewLifecycleOwner) {
      if (it != null) {
        upcoming_adapter.submitList(it)
      }
    }

    viewModel.savedelections.observe(viewLifecycleOwner){
      Log.i("LOO + Saved " ,it.toString() )
      saved_adpter.submitList(it)
    }



    return binding.root
  }

  @RequiresApi(Build.VERSION_CODES.O)
  override fun onResume() {

    super.onResume()
    viewModel.UpdateData()
  }

}
