package com.example.favorite.fragment

import android.bluetooth.BluetoothAdapter
import android.database.ContentObserver
import android.database.Cursor
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.favorite.Movie
import com.example.favorite.R
import com.example.favorite.adapter.CardviewMoview
import com.example.favorite.db.DatabaseContract
import com.example.favorite.helper.MappingHelper
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_movie.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MovieFragment : Fragment() {
    private lateinit var adapter: CardviewMoview
    private lateinit var progressBar: ProgressBar
    private val list= ArrayList<Movie>()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_movie, container, false)



        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rv_movies = view.findViewById<View>(R.id.rv_movies) as RecyclerView

        rv_movies.layoutManager = LinearLayoutManager(context)
        rv_movies.setHasFixedSize(true)
        adapter = CardviewMoview()
        rv_movies.adapter = adapter

        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)

        val myObserver = object : ContentObserver(handler) {
            override fun onChange(self: Boolean) {
                loadNotesAsync()
            }
        }

        context!!.contentResolver.registerContentObserver(DatabaseContract.MovieColumns.CONTENT_URI, true, myObserver)

        if (savedInstanceState == null) {
            loadNotesAsync()
        } else {
            val list = savedInstanceState.getParcelableArrayList<Movie>(BluetoothAdapter.EXTRA_STATE)
            if (list != null) {
                adapter.listMovies = list
            }
        }
    }

    private fun loadNotesAsync() {
        GlobalScope.launch(Dispatchers.Main) {

            val deferredMovies = async(Dispatchers.IO) {
                // CONTENT_URI = content://com.dicoding.picodiploma.mynotesapp/note
                val cursor = context!!.contentResolver?.query(DatabaseContract.MovieColumns.CONTENT_URI, null, null, null, null) as Cursor
                MappingHelper.mapCursorToArrayList(cursor)
            }
            val movie = deferredMovies.await()
            if (movie.size > 0) {
                adapter.listMovies = movie
            } else {
                adapter.listMovies = ArrayList()
                showSnackbarMessage("Tidak ada data saat ini")
            }
        }


    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(BluetoothAdapter.EXTRA_STATE, adapter.listMovies)
    }

    /**
     * Tampilkan snackbar
     *
     * @param message inputan message
     */
    private fun showSnackbarMessage(message: String) {
        Snackbar.make(rv_movies, message, Snackbar.LENGTH_SHORT).show()
    }

}

