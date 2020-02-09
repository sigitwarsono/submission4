package com.example.submission4.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.submission4.Detail.MovieDetail
import com.example.submission4.Movie
import com.example.submission4.R
import kotlinx.android.synthetic.main.item_cardview_movie.view.*


class MovieAdapter :RecyclerView.Adapter<MovieAdapter.CardViewViewHolder>(){
    private lateinit var progressBar: ProgressBar
    private val mData = ArrayList<Movie>()

    fun setData(item: ArrayList<Movie>){
        mData.clear()
        mData.addAll(item)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int):CardViewViewHolder {
        val mView = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_cardview_movie, viewGroup, false)
   return CardViewViewHolder(mView)
    }

    override fun getItemCount(): Int = mData.size

    override fun onBindViewHolder(cardViewViewHolder:CardViewViewHolder, position: Int) {
        cardViewViewHolder.bind(mData[position])
    }
inner class CardViewViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    fun bind(movie: Movie){
        with(itemView){
            tv_title.text = movie.title
            tv_storyline.text=movie.overview
            Glide.with(itemView.context)
                .load("https://image.tmdb.org/t/p/w185/${movie.poster}")
                .placeholder(R.color.colorAccent)
                .dontAnimate()
                .into(img_poster)

            btn_detail.setOnClickListener{


                val intent = Intent(itemView.context, MovieDetail::class.java)
                val listMovie = ArrayList<Movie>()



                listMovie.add(movie)
                intent.putExtra(MovieDetail. EXTRA_MOVIE, movie)
                itemView.context.startActivity(intent)

            }

        }
    }

}

}