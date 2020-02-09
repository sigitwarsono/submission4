package com.example.submission4.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submission4.DetailFavorite.TvDetailFavorite
import com.example.submission4.R
import com.example.submission4.TVshow
import kotlinx.android.synthetic.main.activity_cardview_tv.view.*
import kotlinx.android.synthetic.main.item_cardview_movie.view.*
import kotlinx.android.synthetic.main.item_cardview_movie.view.btn_detail
import kotlinx.android.synthetic.main.item_cardview_movie.view.tv_storyline

class CardviewTv: RecyclerView.Adapter<CardviewTv.TvViewholder>() {
    var listTvshow = ArrayList<TVshow>()
        set(listTvshow) {
            this.listTvshow.clear()
            this.listTvshow.addAll(listTvshow)
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvViewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_cardview_tv, parent, false)
        return TvViewholder(view)
    }

    override fun onBindViewHolder(holder: TvViewholder, position: Int) {
        holder.bind(listTvshow[position])
    }

    override fun getItemCount(): Int {
        return this.listTvshow.size
    }

    inner class TvViewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(tvshow: TVshow) {
            with(itemView) {
                tv_name.text = tvshow.name
                tv_storyline.text = tvshow.overview
                Glide.with(itemView.context)
                    .load("https://image.tmdb.org/t/p/w185/${tvshow.poster}")
                    .placeholder(R.color.colorAccent)
                    .dontAnimate()
                    .into(tv_poster)

                btn_detail.setOnClickListener{

                    val intent = Intent(itemView.context, TvDetailFavorite::class.java)
                    val listTvshow = ArrayList<TVshow>()



                    listTvshow.add(tvshow)
                    intent.putExtra(TvDetailFavorite. EXTRA_TV, tvshow)
                    itemView.context.startActivity(intent)

                }
            }
        }
    }
}
