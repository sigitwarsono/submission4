package com.example.submission4.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submission4.Detail.TvDetail
import com.example.submission4.R
import com.example.submission4.TVshow
import kotlinx.android.synthetic.main.item_cardview_movie.view.*
import kotlinx.android.synthetic.main.item_cardview_movie.view.btn_detail
import kotlinx.android.synthetic.main.item_cardview_movie.view.img_poster
import kotlinx.android.synthetic.main.item_cardview_movie.view.tv_storyline
import kotlinx.android.synthetic.main.item_cardview_tv.view.*


class TvshowAdapter :RecyclerView.Adapter<TvshowAdapter.CardViewViewHolder>(){
    private val mData = ArrayList<TVshow>()

    fun setData(item: ArrayList<TVshow>){
        mData.clear()
        mData.addAll(item)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int):CardViewViewHolder {
        val mView = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_cardview_tv, viewGroup, false)
        return CardViewViewHolder(mView)
    }

    override fun getItemCount(): Int = mData.size

    override fun onBindViewHolder(cardViewViewHolder:CardViewViewHolder, position: Int) {
        cardViewViewHolder.bind(mData[position])
    }
    inner class CardViewViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(tvshow: TVshow){
            with(itemView){
                tv_name.text = tvshow.name
                tv_storyline.text=tvshow.overview
                Glide.with(itemView.context)
                    .load("https://image.tmdb.org/t/p/w185/${tvshow.poster}")
                    .placeholder(R.color.colorAccent)
                    .dontAnimate()
                    .into(tv_poster)

                btn_detail.setOnClickListener{

                    val intent = Intent(itemView.context, TvDetail::class.java)
                    val listTv = ArrayList<TVshow>()

                    listTv.add(tvshow)
                    intent.putExtra(TvDetail. EXTRA_TV, tvshow)
                    itemView.context.startActivity(intent)
                }

            }
        }

    }


}