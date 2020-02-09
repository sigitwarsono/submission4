package com.example.submission4.Detail

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.submission4.R
import com.example.submission4.TVshow
import com.example.submission4.db.DatabaseContract
import com.example.submission4.db.DatabaseContract.TvColumns.Companion.FIRST_AIR_DATE_TV
import com.example.submission4.db.DatabaseContract.TvColumns.Companion.NAME_TV
import com.example.submission4.db.DatabaseContract.TvColumns.Companion.ORIGIN_COUNTRY_TV
import com.example.submission4.db.DatabaseContract.TvColumns.Companion.OVERVIEW_TV
import com.example.submission4.db.DatabaseContract.TvColumns.Companion.POSTER_TV
import com.example.submission4.db.DatabaseContract.TvColumns.Companion._ID_TV
import kotlinx.android.synthetic.main.activity_movie_detail.*
import kotlinx.android.synthetic.main.activity_movie_detail.btn_deskripsi
import kotlinx.android.synthetic.main.activity_tv_detail.*

class TvDetail : AppCompatActivity() {
    internal var tv: Int= 0
    internal var poster: String? = null
    internal var name: String? = null
    internal var date: String? = null
    internal var origin_country: String? = null
    internal var overview: String? = null
    companion object{
        const val EXTRA_TV ="extra_tv"
    }
    private lateinit var progressBar : ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tv_detail)
        progressBar = findViewById(R.id.progressBar)
        progressBar.visibility = View.VISIBLE
        val handler = Handler()
        Thread(Runnable {
            try {
                Thread.sleep(1000)
            } catch (e: Exception) {

            }
            handler.post {
        val rvNama: TextView = findViewById(R.id.btn_name)
        val rvOrigin: TextView = findViewById(R.id.btn_origin)
        val rvTayang: TextView = findViewById(R.id.btn_release)
        val rvDeskripsi: TextView = findViewById(R.id.btn_deskripsi)
        val rvImage: ImageView = findViewById(R.id.btn_img)


        val tvshow = intent.getParcelableExtra(EXTRA_TV) as TVshow

        name = tvshow.name
        rvNama.text = name

        origin_country = tvshow.origin_country
        rvOrigin.text = origin_country

        date = tvshow.first_air_date
        rvTayang.text = date

        overview = tvshow.overview
        rvDeskripsi.text = overview

        poster = tvshow.poster
        Glide.with(applicationContext).load("https://image.tmdb.org/t/p/w185/${tvshow.poster}").into(rvImage)
                progressBar.visibility = View.INVISIBLE


    }
        }).start()

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_tv, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.favorite_tv) {

            val name = btn_name.text.toString().trim()
            val Origin = btn_origin.text.toString().trim()
            val Tayang = btn_release.text.toString().trim()
            val Deskripsi= btn_deskripsi.text.toString().trim()
            val img= btn_img.toString()


            val values = ContentValues()
            values.put(_ID_TV, tv)
            values.put(NAME_TV,name)
            values.put(ORIGIN_COUNTRY_TV,Origin)
            values.put(FIRST_AIR_DATE_TV,Tayang)
            values.put(OVERVIEW_TV,Deskripsi)
            values.put(POSTER_TV,img)
            values.put(POSTER_TV,poster)


            // Gunakan content uri untuk insert
            // content://com.dicoding.picodiploma.mynotesapp/note/
            contentResolver.insert(DatabaseContract.TvColumns.CONTENT_URI_TV, values)
            Toast.makeText(this, "Satu item favorite berhasil disimpan", Toast.LENGTH_SHORT).show()
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

}

