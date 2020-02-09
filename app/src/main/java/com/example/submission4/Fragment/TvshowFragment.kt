package com.example.submission4.Fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.submission4.Adapter.TvshowAdapter
import com.example.submission4.R
import com.example.submission4.viewmodel.TvViewModel


/**
 * A simple [Fragment] subclass.
 *
 */
class TvshowFragment : Fragment() {

    private lateinit var adapter: TvshowAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var tvViewModel: TvViewModel

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_tvshow, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<View>(R.id.rv_movies) as RecyclerView
        adapter = TvshowAdapter()
        adapter.notifyDataSetChanged()
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
        progressBar = view.findViewById(R.id.progressBar)

        tvViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(TvViewModel::class.java)
        tvViewModel.getTv().observe(this, Observer { moviesItem ->
            if (moviesItem != null) {
                adapter.setData(moviesItem)
            }
            showLoading(false)
        })
        tvViewModel.setTv()
        showLoading(true)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }



}
