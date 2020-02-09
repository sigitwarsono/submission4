package com.example.submission4.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

internal class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {

        private const val DATABASE_NAME = "ayobisa"

        private const val DATABASE_VERSION = 1

        private val SQL_CREATE_TABLE_MOVIE ="CREATE TABLE ${DatabaseContract.MovieColumns.TABLE_NAME}" +
                " (${DatabaseContract.MovieColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                " ${DatabaseContract.MovieColumns.TITLE} TEXT NOT NULL," +
                " ${DatabaseContract.MovieColumns.OVERVIEW} TEXT NOT NULL," +
                " ${DatabaseContract.MovieColumns.VOTE_AVERAGE} TEXT NOT NULL,"+
                " ${DatabaseContract.MovieColumns.RELEASE_DATE} TEXT NOT NULL,"+
                " ${DatabaseContract.MovieColumns.POSTER} BLOB NOT NULL)"

        private val SQL_CREATE_TABLE_TV ="CREATE TABLE ${DatabaseContract.TvColumns.TABLE_NAME_TV}" +
                " (${DatabaseContract.TvColumns._ID_TV} INTEGER PRIMARY KEY AUTOINCREMENT," +
                " ${DatabaseContract.TvColumns.NAME_TV} TEXT NOT NULL," +
                " ${DatabaseContract.TvColumns.OVERVIEW_TV} TEXT NOT NULL," +
                " ${DatabaseContract.TvColumns.ORIGIN_COUNTRY_TV} TEXT NOT NULL,"+
                " ${DatabaseContract.TvColumns.FIRST_AIR_DATE_TV} TEXT NOT NULL,"+
                " ${DatabaseContract.TvColumns.POSTER_TV} BLOB NOT NULL)"
    }


    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_MOVIE)
        db.execSQL(SQL_CREATE_TABLE_TV)
    }

    /*
    Method onUpgrade akan di panggil ketika terjadi perbedaan versi
    Gunakan method onUpgrade untuk melakukan proses migrasi data
     */
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        /*
        Drop table tidak dianjurkan ketika proses migrasi terjadi dikarenakan data user akan hilang,
        */
        db.execSQL("DROP TABLE IF EXISTS ${DatabaseContract.MovieColumns.TABLE_NAME}")
        db.execSQL("DROP TABLE IF EXISTS ${DatabaseContract.TvColumns.TABLE_NAME_TV}")
        onCreate(db)
    }
}
