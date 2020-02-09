package com.example.submission4.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submission4.Movie
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject


class MovieViewModel: ViewModel() {
    companion object {
        private const val API_KEY = "6084885a4f338c0c77665c13c5fea707"
    }
    val listMovies = MutableLiveData<ArrayList<Movie>>()

    internal fun setMovies(){
        val client = AsyncHttpClient()
        val listItem =  ArrayList<Movie>()
        val url = "https://api.themoviedb.org/3/discover/movie?api_key=$API_KEY&language=en-US"

        client.get(url, object : AsyncHttpResponseHandler(){
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                try {
                    val result = String(responseBody)
                    val responseObject = JSONObject(result)
                    val list = responseObject.getJSONArray("results")
                    for (i in 0 until list.length()){
                        val movie = list.getJSONObject(i)
                        val movieItems = Movie(
                            title = movie.getString("title"),
                            overview = movie.getString("overview"),
                            vote_average = movie.getString("vote_average"),
                            release_date = movie.getString("release_date"),
                            poster = movie.getString("poster_path")

                        )
                        listItem.add(movieItems)
                    }
                    listMovies.postValue(listItem)

                } catch (e: Exception){
                    Log.d("Exeption", e.message.toString())
                }

            }

            override fun onFailure(statusCode: Int,headers: Array<Header>,responseBody: ByteArray,error: Throwable) {
                Log.d("onFailure", error.message.toString())
            }
        })

    }

    internal fun getMovies(): LiveData<ArrayList<Movie>> {
        return listMovies
    }
}