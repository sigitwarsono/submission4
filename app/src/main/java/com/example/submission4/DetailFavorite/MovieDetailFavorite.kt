package com.example.submission4.DetailFavorite

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
import com.example.submission4.HapusMovie
import com.example.submission4.HapusTv
import com.example.submission4.Movie
import com.example.submission4.R
import com.example.submission4.db.DatabaseContract
import com.example.submission4.helper.MappingHelper

class MovieDetailFavorite : AppCompatActivity() {

    internal var poster: String? = null
    internal var title: String? = null
    internal var release: String? = null
    internal var vote_Average: String? = null
    internal var overview: String? = null

    companion object{
        const val EXTRA_MOVIE ="extra_movie"
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20
        const val EXTRA_POSITION = "extra_position"
    }

    private lateinit var uriWithId: Uri
    private var isEdit = false
    private var movie: Movie? = null
    private var position: Int = 0
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
                uriWithId = Uri.parse(DatabaseContract.MovieColumns.CONTENT_URI.toString() + "/" + movie?.id)
                val cursor = contentResolver.query(uriWithId, null, null, null, null)
                if (cursor != null) {
                    MappingHelper.mapCursorToObject(cursor)
                    cursor.close()
                }


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
        movie = intent.getParcelableExtra(HapusMovie.EXTRA_NOTE)

        menuInflater.inflate(R.menu.menu_form, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete -> showAlertDialog(ALERT_DIALOG_DELETE)
            android.R.id.home -> showAlertDialog(ALERT_DIALOG_CLOSE)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        showAlertDialog(HapusTv.ALERT_DIALOG_CLOSE)
    }

    private fun showAlertDialog(type: Int) {
        val isDialogClose = type == ALERT_DIALOG_CLOSE
        val dialogTitle: String
        val dialogMessage: String

        if (isDialogClose) {
            dialogTitle = "Batal"
            dialogMessage = "Apakah anda ingin kembali?"
        } else {
            dialogMessage = "Apakah anda yakin ingin menghapus item ini?"
            dialogTitle = "Hapus Movie"
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
