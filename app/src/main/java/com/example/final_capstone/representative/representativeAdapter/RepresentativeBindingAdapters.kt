package com.example.final_capstone.representative.representativeAdapter


import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.final_capstone.R

@BindingAdapter("profileImage")
fun fetchImage(view: ImageView, src: String?) {
  Log.i("Profile" , src.toString())
  src?.let {
    val uri = src.toUri().buildUpon().scheme("https").build()
    Glide.with(view.context)
      .load(uri)
      .placeholder(R.mipmap.placeholdericon)
      .override(300, 200)
      .into(view)
      ;  }
  if(src==null){
    view.setImageResource(R.mipmap.placeholdericon)
  }

}

@BindingAdapter("stateValue")
fun Spinner.setNewValue(value: String?) {
  val adapter = toTypedAdapter<String>(this.adapter as ArrayAdapter<*>)
  val position = when (adapter.getItem(0)) {
    is String -> adapter.getPosition(value)
    else -> this.selectedItemPosition
  }
  if (position >= 0) {
    setSelection(position)
  }
}

inline fun <reified T> toTypedAdapter(adapter: ArrayAdapter<*>): ArrayAdapter<T>{
  return adapter as ArrayAdapter<T>
}
