package com.example.submission4.Fragment


import android.bluetooth.BluetoothAdapter
import android.database.ContentObserver
import android.database.Cursor
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.submission4.Adapter.CardviewTv

import com.example.submission4.R
import com.example.submission4.TVshow
import com.example.submission4.db.DatabaseContract
import com.example.submission4.helper.TvMappingHelper
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_movie.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 */
class TvshowFragment1 : Fragment() {
    private lateinit var adapter: CardviewTv
    private lateinit var progressBar: ProgressBar
    private val list= ArrayList<TVshow>()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_tvshow_fragment1, container, false)



        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rv_tv = view.findViewById<View>(R.id.rv_movies) as RecyclerView

        rv_tv.layoutManager = LinearLayoutManager(context)
        rv_tv.setHasFixedSize(true)
        adapter = CardviewTv()
        rv_tv.adapter = adapter

        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)

        val myObserver = object : ContentObserver(handler) {
            override fun onChange(self: Boolean) {
                loadTvshowAsync()
            }
        }

        context!!.contentResolver.registerContentObserver(DatabaseContract.TvColumns.CONTENT_URI_TV, true, myObserver)

        if (savedInstanceState == null) {
            loadTvshowAsync()
        } else {
            val list = savedInstanceState.getParcelableArrayList<TVshow>(BluetoothAdapter.EXTRA_STATE)
            if (list != null) {
                adapter.listTvshow = list
            }
        }
    }

    private fun loadTvshowAsync() {
        GlobalScope.launch(Dispatchers.Main) {

            val deferredTVshow = async(Dispatchers.IO) {
                // CONTENT_URI = content://com.dicoding.picodiploma.mynotesapp/note
                val cursor = context!!.contentResolver?.query(DatabaseContract.TvColumns.CONTENT_URI_TV, null, null, null, null) as Cursor
                TvMappingHelper.mapCursorToArrayList(cursor)
            }
            val tvshow = deferredTVshow.await()
            if (tvshow.size > 0) {
                adapter.listTvshow = tvshow
            } else {
                adapter.listTvshow = ArrayList()
                showSnackbarMessage("Tidak ada data saat ini")
            }
        }


    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(BluetoothAdapter.EXTRA_STATE, adapter.listTvshow)
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




