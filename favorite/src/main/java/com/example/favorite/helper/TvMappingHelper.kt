package com.example.favorite.helper

import android.database.Cursor
import com.example.favorite.Movie
import com.example.favorite.TVshow
import com.example.favorite.db.DatabaseContract
import com.example.favorite.db.DatabaseContract.MovieColumns.Companion.OVERVIEW
import com.example.favorite.db.DatabaseContract.MovieColumns.Companion.POSTER
import com.example.favorite.db.DatabaseContract.MovieColumns.Companion._ID
import com.example.favorite.db.DatabaseContract.TvColumns.Companion.FIRST_AIR_DATE_TV
import com.example.favorite.db.DatabaseContract.TvColumns.Companion.NAME_TV
import com.example.favorite.db.DatabaseContract.TvColumns.Companion.ORIGIN_COUNTRY_TV
import com.example.favorite.db.DatabaseContract.TvColumns.Companion.OVERVIEW_TV
import com.example.favorite.db.DatabaseContract.TvColumns.Companion.POSTER_TV
import com.example.favorite.db.DatabaseContract.TvColumns.Companion._ID_TV


object TvMappingHelper {


    fun mapCursorToArrayList(tvCursor: Cursor): ArrayList<TVshow> {
        val tvList = ArrayList<TVshow>()

        while (tvCursor.moveToNext()) {
            val id = tvCursor.getInt(tvCursor.getColumnIndexOrThrow(_ID_TV))
            val name = tvCursor.getString(tvCursor.getColumnIndexOrThrow(NAME_TV))
            val overview = tvCursor.getString(tvCursor.getColumnIndexOrThrow(OVERVIEW_TV))
            val origin_country = tvCursor.getString(tvCursor.getColumnIndexOrThrow(ORIGIN_COUNTRY_TV))
            val first_air_date = tvCursor.getString(tvCursor.getColumnIndexOrThrow(FIRST_AIR_DATE_TV))
            val poster = tvCursor.getString(tvCursor.getColumnIndexOrThrow(POSTER_TV))
            tvList.add(TVshow(id, name,overview,origin_country,first_air_date,poster))
        }

        return tvList
    }

    fun mapCursorToObject(tvCursor: Cursor): TVshow {
        tvCursor.moveToNext()
        val id = tvCursor.getInt(tvCursor.getColumnIndexOrThrow(_ID_TV))
        val name = tvCursor.getString(tvCursor.getColumnIndexOrThrow(NAME_TV))
        val overview = tvCursor.getString(tvCursor.getColumnIndexOrThrow(OVERVIEW_TV))
        val origin_country = tvCursor.getString(tvCursor.getColumnIndexOrThrow(ORIGIN_COUNTRY_TV))
        val first_air_date = tvCursor.getString(tvCursor.getColumnIndexOrThrow(FIRST_AIR_DATE_TV))
        val poster = tvCursor.getString(tvCursor.getColumnIndexOrThrow(POSTER_TV))
        return TVshow(id, name, overview,origin_country,first_air_date,poster)
    }


}