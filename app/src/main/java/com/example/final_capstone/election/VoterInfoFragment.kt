package com.example.final_capstone.election

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.final_capstone.database.ElectionDatabase
import com.example.final_capstone.databinding.FragmentVoterInfoBinding


class VoterInfoFragment : Fragment() {
private lateinit var binding : FragmentVoterInfoBinding
  private val navController by lazy { findNavController() }

  private val viewModel by viewModels<VoterInfoViewModel> {
    VoterInfoViewModelFactory(ElectionDatabase.getInstance(requireContext()).electionDao)
  }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
      binding = FragmentVoterInfoBinding.inflate(inflater)
      val arguments = VoterInfoFragmentArgs.fromBundle(requireArguments())
      viewModel.UpdateVoterInfo(arguments.selectedElection)
      viewModel.setFollow()

      viewModel.VoterInfo.observe(viewLifecycleOwner){
        binding.election = it
      }

      viewModel.Followed.observe(viewLifecycleOwner){
        if (it == true){
          binding.FollowElectionButton.text = "UNFOLLOW ELECTION"
        }else
          binding.FollowElectionButton.text = "FOLLOW ELECTION"

      }
      binding.FollowElectionButton.setOnClickListener {
        viewModel.ChangeFollowElection()
      }
      viewModel.url.observe(viewLifecycleOwner){
        Log.i("LOO" , "clicked")

        if(it!=null){
          Log.d("YEES " ,it)

          openWebURL(it)
          viewModel.UrlCompleted()

        }
      }

      if (viewModel.Followed.value == true){
        binding.FollowElectionButton.text = "UNFOLLOW ELECTION"
      }else
        binding.FollowElectionButton.text = "FOLLOW ELECTION"

binding.VotingLocations.setOnClickListener {
  if(viewModel.administrationBody?.value?.votingLocationFinderUrl!=null)
  viewModel.goToUrl(viewModel.administrationBody?.value?.votingLocationFinderUrl.toString())
  else
    Toast.makeText(requireActivity() , "No Url Found" ,Toast.LENGTH_LONG).show()
}

      binding.BallotInformation.setOnClickListener {
        if (viewModel.administrationBody?.value?.ballotInfoUrl!=null)
        viewModel.goToUrl(viewModel.administrationBody?.value?.ballotInfoUrl.toString())
        else
        Toast.makeText(requireActivity() , "No Url Found" ,Toast.LENGTH_LONG).show()
      }
navController.enableOnBackPressed(true)
        return binding.root
    }
  fun openWebURL(inURL: String?) {
    Log.i("LOO" , inURL.toString())

    val browse = Intent(Intent.ACTION_VIEW, Uri.parse(inURL))
    startActivity(browse)
  }

}
