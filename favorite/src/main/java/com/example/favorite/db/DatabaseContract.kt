package com.example.favorite.db

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {

    const val AUTHORITY = "com.example.submission4"
    const val SCHEME = "content"
    const val AUTHORITY_TV = "com.example.submission4.tv"

    class MovieColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "movie"
            const val _ID = "_id"
            const val TITLE = "title"
            const val OVERVIEW = "overview"
            const val VOTE_AVERAGE = "vote_average"
            const val RELEASE_DATE = "release_date"
            const val POSTER = "poster"

            // untuk membuat URI content://com.dicoding.picodiploma.mynotesapp/note
            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }
    }

    class TvColumns : BaseColumns {
        companion object {
            const val TABLE_NAME_TV = "tv"
            const val _ID_TV = "_id"
            const val NAME_TV = "name"
            const val OVERVIEW_TV = "overview"
            const val ORIGIN_COUNTRY_TV = "origin_country"
            const val FIRST_AIR_DATE_TV = "first_air_date"
            const val POSTER_TV = "poster"

            // untuk membuat URI content://com.dicoding.picodiploma.mynotesapp/note
            val CONTENT_URI_TV: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY_TV)
                .appendPath(TABLE_NAME_TV)
                .build()
        }
    }
}