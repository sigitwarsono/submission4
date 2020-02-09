package com.example.submission4.helper

import android.database.Cursor
import com.example.submission4.Movie
import com.example.submission4.db.DatabaseContract.MovieColumns.Companion.OVERVIEW
import com.example.submission4.db.DatabaseContract.MovieColumns.Companion.POSTER
import com.example.submission4.db.DatabaseContract.MovieColumns.Companion.RELEASE_DATE
import com.example.submission4.db.DatabaseContract.MovieColumns.Companion.TITLE
import com.example.submission4.db.DatabaseContract.MovieColumns.Companion.VOTE_AVERAGE
import com.example.submission4.db.DatabaseContract.MovieColumns.Companion._ID

object MappingHelper {

    fun mapCursorToArrayList(moviesCursor: Cursor): ArrayList<Movie> {
        val moviesList = ArrayList<Movie>()

        while (moviesCursor.moveToNext()) {
            val id = moviesCursor.getInt(moviesCursor.getColumnIndexOrThrow(_ID))
            val title = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(TITLE))
            val overview = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(OVERVIEW))
            val vote_average = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(VOTE_AVERAGE))
            val release_date = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(RELEASE_DATE))
            val poster = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(POSTER))
            moviesList.add(Movie(id, title, overview, vote_average, release_date, poster))
        }

        return moviesList
    }

    fun mapCursorToObject(moviesCursor: Cursor): Movie {
        moviesCursor.moveToNext()
        val id = moviesCursor.getInt(moviesCursor.getColumnIndexOrThrow(_ID))
        val title = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(TITLE))
        val overview = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(OVERVIEW))
        val vote_average = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(VOTE_AVERAGE))
        val release_date = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(RELEASE_DATE))
        val poster = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(POSTER))
        return Movie(id, title, overview, vote_average, release_date, poster)
    }


}