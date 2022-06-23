package com.example.final_capstone.representative.representativeAdapter

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.final_capstone.databinding.RepresentaiveItemBinding
import com.example.final_capstone.network.models.Channel
import com.example.final_capstone.representative.model.Representative
class RepresentativeViewHolder constructor(private val binding:RepresentaiveItemBinding):RecyclerView.ViewHolder(binding.root) {

  fun bind(item: Representative) {
    binding.representaive = item
    val socialMedia =showLinks(binding,item.official.channels);
setClickListeners(item , socialMedia )

    binding.executePendingBindings()
  }

  private fun GetUrls(item: Representative): List<String>? {
return item.official.urls
  }

  private fun setIntent(url: String) {
    val uri = Uri.parse(url)
    val intent = Intent(ACTION_VIEW, uri)
    itemView.context.startActivity(intent)
  }
  private fun setClickListeners(item: Representative, socialMedia: HashMap<String, String>) {
    if(socialMedia.get("Facebook") == ""){
      binding.imageFacebookIcon.visibility= View.GONE}
    else{
      binding.imageFacebookIcon.setOnClickListener {
        setIntent("https://facebook.com/"+socialMedia.get("Facebook"))
      }
      binding.imageFacebookIcon.visibility= View.VISIBLE}

    if(socialMedia.get("Twitter") == ""){
      binding.imageTwitterIcon.visibility= View.GONE}
    else {
      binding.imageTwitterIcon.visibility = View.VISIBLE
      binding.imageTwitterIcon.setOnClickListener {
        setIntent("https://twitter.com/" + socialMedia.get("Twitter"))
      }
    }
    val urls= GetUrls(item)
    if(!urls.isNullOrEmpty()){
      binding.websiteIcon.visibility = View.VISIBLE
      binding.websiteIcon.setOnClickListener {
        setIntent(urls.first())
      }
    }else {
      binding.websiteIcon.visibility = View.GONE
    }

  }

  private fun showLinks(binding: RepresentaiveItemBinding, channels: List<Channel>?): HashMap<String, String> {
    val arrayList = HashMap<String , String>()//Creating an empty Hashset
arrayList.put("Facebook" , "")
    arrayList.put("Twitter" , "")
    if (channels != null) {


      for(channel in channels){
      if(channel.type.equals("Facebook")){
        Log.i("LOO" , channel.type)

        arrayList.replace("Facebook" , channel.id)
      }
        if(channel.type.equals("Twitter")){
          arrayList.replace("Twitter" , channel.id)

        }
      }
    }
return arrayList
  }
  fun getFacebookUrl (id : String){

  }

  companion object {
    fun create(parent: ViewGroup): RepresentativeViewHolder {
      val layoutInflater = LayoutInflater.from(parent.context)
      val binding = RepresentaiveItemBinding.inflate(layoutInflater, parent, false)
      return RepresentativeViewHolder(binding)
    }
  }
}
