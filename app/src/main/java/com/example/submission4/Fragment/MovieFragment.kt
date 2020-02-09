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
import com.example.submission4.Adapter.MovieAdapter
import com.example.submission4.R
import com.example.submission4.viewmodel.MovieViewModel


/**
 * A simple [Fragment] subclass.
 *
 */
class MovieFragment : Fragment() {
    private lateinit var adapter: MovieAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var moviesViewModel: MovieViewModel

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_movie, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<View>(R.id.rv_movies) as RecyclerView
        adapter = MovieAdapter()
        adapter.notifyDataSetChanged()
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
        progressBar = view.findViewById(R.id.progressBar)

        moviesViewModel = ViewModelProvider(this,ViewModelProvider.NewInstanceFactory()).get(MovieViewModel::class.java)
        moviesViewModel.getMovies().observe(this, Observer { moviesItem ->
            if (moviesItem != null) {
                adapter.setData(moviesItem)
            }

        })
        moviesViewModel.setMovies()
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
