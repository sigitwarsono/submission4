package com.example.submission4.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submission4.TVshow
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class TvViewModel: ViewModel() {
    companion object {
        private const val API_KEY = "6084885a4f338c0c77665c13c5fea707"
    }
    val listTv = MutableLiveData<ArrayList<TVshow>>()

    internal fun setTv(){
        val client = AsyncHttpClient()
        val listItem =  ArrayList<TVshow>()
        val url = "https://api.themoviedb.org/3/discover/tv?api_key=$API_KEY&language=en-US"

        client.get(url, object : AsyncHttpResponseHandler(){
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                try {
                    val result = String(responseBody)
                    val responseObject = JSONObject(result)
                    val list = responseObject.getJSONArray("results")
                    for (i in 0 until list.length()){
                        val tv = list.getJSONObject(i)
                        val tvItems = TVshow(
                            name = tv.getString("name"),
                            overview = tv.getString("overview"),
                            origin_country = tv.getString("origin_country"),
                            first_air_date = tv.getString("first_air_date"),
                            poster = tv.getString("poster_path")
                        )
                        listItem.add(tvItems)
                    }
                    listTv.postValue(listItem)

                } catch (e: Exception){
                    Log.d("Exeption", e.message.toString())
                }

            }

            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                Log.d("onFailure", error.message.toString())
            }
        })

    }

    internal fun getTv(): LiveData<ArrayList<TVshow>> {
        return listTv
    }
}