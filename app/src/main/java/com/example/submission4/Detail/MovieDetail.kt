package com.example.submission4.Detail

import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.submission4.Movie
import com.example.submission4.R
import com.example.submission4.db.DatabaseContract.MovieColumns.Companion.CONTENT_URI
import com.example.submission4.db.DatabaseContract.MovieColumns.Companion.OVERVIEW
import com.example.submission4.db.DatabaseContract.MovieColumns.Companion.POSTER
import com.example.submission4.db.DatabaseContract.MovieColumns.Companion.RELEASE_DATE
import com.example.submission4.db.DatabaseContract.MovieColumns.Companion.TITLE
import com.example.submission4.db.DatabaseContract.MovieColumns.Companion.VOTE_AVERAGE
import com.example.submission4.db.DatabaseContract.MovieColumns.Companion._ID
import kotlinx.android.synthetic.main.activity_movie_detail.*
import kotlinx.android.synthetic.main.activity_movie_detail.btn_deskripsi
import kotlinx.android.synthetic.main.activity_tv_detail.*
import kotlinx.android.synthetic.main.item_cardview_movie.view.*
import java.io.ByteArrayOutputStream
import java.io.File


class MovieDetail : AppCompatActivity() {

    internal var movie: Int= 0
    internal var poster: String? = null
    internal var title: String? = null
    internal var release: String? = null
    internal var vote_Average: String? = null
    internal var overview: String? = null
    companion object{
        const val EXTRA_MOVIE ="extra_movie"
    }


    private lateinit var progressBar : ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        progressBar = findViewById(R.id.progressBar)
        progressBar.visibility = View.VISIBLE
        val handler = Handler()
        Thread(Runnable {
            try {
                Thread.sleep(1000)
            } catch (e: Exception) {

            }
            handler.post {
                val rvNama: TextView = findViewById(R.id.btn_title)
                val rvAverage: TextView = findViewById(R.id.btn_average)
                val rvTayang: TextView = findViewById(R.id.btn_tayang)
                val rvDeskripsi: TextView = findViewById(R.id.btn_deskripsi)
                val rvImage: ImageView = findViewById(R.id.btn_image)


                val movie = intent.getParcelableExtra(EXTRA_MOVIE) as Movie


                title = movie.title
                rvNama.text = title

                vote_Average = movie.vote_average
                rvAverage.text = vote_Average

                release = movie.release_date
                rvTayang.text = release

                overview = movie.overview
                rvDeskripsi.text = overview

                poster = movie.poster
                Glide.with(applicationContext)
                    .load("https://image.tmdb.org/t/p/w185/${movie.poster}").into(rvImage)
                progressBar.visibility = View.INVISIBLE

            }
        }).start()

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_movie, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.favorite) {
            val title = btn_title.text.toString().trim()
            val description = btn_deskripsi.text.toString().trim()
            val voteAverage = btn_average.text.toString().trim()
            val release= btn_tayang.text.toString().trim()
            val img= btn_image.toString()


            val values = ContentValues()
            values.put(_ID, movie)
            values.put(TITLE,title)
            values.put(OVERVIEW,description)
            values.put(VOTE_AVERAGE,voteAverage)
            values.put(RELEASE_DATE,release)
            values.put(POSTER,img)
            values.put(POSTER,poster)

            // Gunakan content uri untuk insert
            // content://com.dicoding.picodiploma.mynotesapp/note/
            contentResolver.insert(CONTENT_URI, values)
            Toast.makeText(this, "Satu item favorite berhasil disimpan", Toast.LENGTH_SHORT).show()
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

}