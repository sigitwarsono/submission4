package com.example.submission4.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submission4.DetailFavorite.MovieDetailFavorite
import com.example.submission4.Movie
import com.example.submission4.R
import kotlinx.android.synthetic.main.item_cardview_movie.view.*

class CardviewMoview: RecyclerView.Adapter<CardviewMoview.MovieViewholder>() {
    var listMovies = ArrayList<Movie>()
        set(listMovies) {
            this.listMovies.clear()
            this.listMovies.addAll(listMovies)
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_cardview_movie, parent, false)
        return MovieViewholder(view)
    }

    override fun onBindViewHolder(holder: MovieViewholder, position: Int) {
        holder.bind(listMovies[position])
    }

    override fun getItemCount(): Int {
        return this.listMovies.size
    }

    inner class MovieViewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movie:Movie) {
            with(itemView) {
                tv_title.text = movie.title
                tv_storyline.text = movie.overview
                Glide.with(itemView.context)
                    .load("https://image.tmdb.org/t/p/w185/${movie.poster}")
                    .placeholder(R.color.colorAccent)
                    .dontAnimate()
                    .into(img_poster)

                btn_detail.setOnClickListener{

                    val intent = Intent(itemView.context, MovieDetailFavorite::class.java)
                    val listMovie = ArrayList<Movie>()



                    listMovie.add(movie)
                    intent.putExtra(MovieDetailFavorite. EXTRA_MOVIE, movie)
                    itemView.context.startActivity(intent)

                }
            }
        }
    }
}
