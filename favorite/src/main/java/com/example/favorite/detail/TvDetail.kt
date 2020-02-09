package com.example.favorite.detail

import android.content.ContentValues
import android.net.Uri
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
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.example.favorite.HapusMovie
import com.example.favorite.Movie
import com.example.favorite.R
import com.example.favorite.TVshow
import com.example.favorite.db.DatabaseContract
import com.example.favorite.helper.MappingHelper
import com.example.favorite.helper.TvMappingHelper
import kotlinx.android.synthetic.main.activity_movie_detail.*

class TvDetail : AppCompatActivity() {
    internal var poster: String? = null
    internal var name: String? = null
    internal var date: String? = null
    internal var origin_country: String? = null
    internal var overview: String? = null
    companion object{
        const val EXTRA_TV ="extra_tv"
    }
    private lateinit var progressBar : ProgressBar
    private lateinit var uriWithId: Uri
    private var isEdit = false
    private var tvshow: TVshow? = null
    private var position: Int = 0
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
                uriWithId = Uri.parse(DatabaseContract.TvColumns.CONTENT_URI_TV.toString() + "/" + tvshow?.id)
                val cursor = contentResolver.query(uriWithId, null, null, null, null)
                if (cursor != null) {
                    TvMappingHelper.mapCursorToObject(cursor)
                    cursor.close()
                }

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
        tvshow = intent.getParcelableExtra(HapusMovie.EXTRA_NOTE)

        menuInflater.inflate(R.menu.menu_form, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete -> showAlertDialog(MovieDetail.ALERT_DIALOG_DELETE)
            android.R.id.home -> showAlertDialog(MovieDetail.ALERT_DIALOG_CLOSE)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        showAlertDialog(HapusMovie.ALERT_DIALOG_CLOSE)
    }

    private fun showAlertDialog(type: Int) {
        val isDialogClose = type == MovieDetail.ALERT_DIALOG_CLOSE
        val dialogTitle: String
        val dialogMessage: String

        if (isDialogClose) {
            dialogTitle = "Batal"
            dialogMessage = "Apakah anda ingin kembali?"
        } else {
            dialogMessage = "Apakah anda yakin ingin menghapus item ini?"
            dialogTitle = "Hapus TVshow"
        }

        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle(dialogTitle)
        alertDialogBuilder
            .setMessage(dialogMessage)
            .setCancelable(false)
            .setPositiveButton("Ya") { dialog, id ->
                if (isDialogClose) {
                    finish()
                } else {
                    // Gunakan uriWithId dari intent activity ini
                    // content://com.dicoding.picodiploma.mynotesapp/note/id
                    contentResolver.delete(uriWithId, null, null)
                    Toast.makeText(this, "Satu item berhasil dihapus", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
            .setNegativeButton("Tidak") { dialog, id -> dialog.cancel() }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

}

