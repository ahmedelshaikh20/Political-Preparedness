package com.example.final_capstone.network


import com.example.android.politicalpreparedness.network.CivicsHttpClient
import com.example.final_capstone.network.jsonadapters.CustomDateAdapter
import com.example.final_capstone.network.jsonadapters.ElectionAdapter
import com.example.final_capstone.network.models.ElectionResponse
import com.example.final_capstone.network.models.RepresentativeResponse
import com.example.final_capstone.network.models.VoterInfoResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://www.googleapis.com/civicinfo/v2/"

val interceptor = HttpLoggingInterceptor().apply {
  this.level = HttpLoggingInterceptor.Level.BODY
};
val client = OkHttpClient()
  .newBuilder()
  .connectTimeout(2, TimeUnit.MINUTES)
  .addInterceptor(interceptor)
  .build()

// TODO: Add adapters for Java Date and custom adapter ElectionAdapter (included in project)
private val moshi = Moshi.Builder()
  .add(ElectionAdapter())
  .add(KotlinJsonAdapterFactory())
  .add(CustomDateAdapter())
  .build()
val contentType = "application/json".toMediaType()

private val retrofit = Retrofit.Builder()
  .addCallAdapterFactory(CoroutineCallAdapterFactory())
  .addConverterFactory(MoshiConverterFactory.create(moshi))
  .client(CivicsHttpClient.getClient())
  .baseUrl(BASE_URL)
  .build()

/**
 *  Documentation for the Google Civics API Service can be found at https://developers.google.com/civic-information/docs/v2
 */

interface CivicsApiService {
  @GET("elections")
  suspend fun getElections(): ElectionResponse
  @GET("elections")
  suspend fun getElectionsStr(): ResponseBody

  @GET("voterinfo")
  suspend fun getVoterInfo(
    @Query("address") address: String,
    @Query("electionId") electionId: Int
  ): VoterInfoResponse


  @GET("representatives")
  suspend fun getRepresentatives(
    @Query("address") address: String
  ): Response<RepresentativeResponse>
}

object CivicsApi {
  val retrofitService: CivicsApiService by lazy {
    retrofit.create(CivicsApiService::class.java)
  }

}
