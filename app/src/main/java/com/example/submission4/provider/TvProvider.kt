package com.example.submission4.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.example.submission4.db.DatabaseContract
import com.example.submission4.db.MovieHelper
import com.example.submission4.db.TvHelper

class TvProvider : ContentProvider() {

    companion object {

        /*
        Integer digunakan sebagai identifier antara select all sama select by id
         */
        private const val TV = 1
        private const val TV_ID = 2
        private lateinit var tvHelper: TvHelper

        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        /*
        Uri matcher untuk mempermudah identifier dengan menggunakan integer
        misal
        uri com.dicoding.picodiploma.mynotesapp dicocokan dengan integer 1
        uri com.dicoding.picodiploma.mynotesapp/# dicocokan dengan integer 2
         */
        init {
            // content://com.dicoding.picodiploma.mynotesapp/note
            sUriMatcher.addURI(
                DatabaseContract.AUTHORITY_TV,
                DatabaseContract.TvColumns.TABLE_NAME_TV, TV)

            // content://com.dicoding.picodiploma.mynotesapp/note/id
            sUriMatcher.addURI(DatabaseContract.AUTHORITY_TV, "${DatabaseContract.TvColumns.TABLE_NAME_TV}/#", TV_ID)
        }
    }

    override fun onCreate(): Boolean {
        tvHelper = TvHelper.getInstance(context as Context)
        tvHelper.open()
        return true
    }

    /*
    Method queryAll digunakan ketika ingin menjalankan queryAll Select
    Return cursor
     */
    override fun query(uri: Uri, strings: Array<String>?, s: String?, strings1: Array<String>?, s1: String?): Cursor? {
        val cursor: Cursor?
        when (sUriMatcher.match(uri)) {
            TV -> cursor = tvHelper.queryAll()
            TV_ID -> cursor = tvHelper.queryById(uri.lastPathSegment.toString())
            else -> cursor = null
        }

        return cursor
    }


    override fun getType(uri: Uri): String? {
        return null
    }


    override fun insert(uri: Uri, contentValues: ContentValues?): Uri? {
        val added: Long = when (TV) {
            sUriMatcher.match(uri) -> tvHelper.insert(contentValues)
            else -> 0
        }

        context?.contentResolver?.notifyChange(DatabaseContract.TvColumns.CONTENT_URI_TV, null)

        return Uri.parse("${DatabaseContract.TvColumns.CONTENT_URI_TV}/$added")
    }


    override fun update(uri: Uri, contentValues: ContentValues?, s: String?, strings: Array<String>?): Int {
        val updated: Int = when (TV_ID) {
            sUriMatcher.match(uri) -> tvHelper.update(uri.lastPathSegment.toString(),contentValues)
            else -> 0
        }

        context?.contentResolver?.notifyChange(DatabaseContract.TvColumns.CONTENT_URI_TV, null)

        return updated
    }

    override fun delete(uri: Uri, s: String?, strings: Array<String>?): Int {
        val deleted: Int = when (TV_ID) {
            sUriMatcher.match(uri) -> tvHelper.deleteById(uri.lastPathSegment.toString())
            else -> 0
        }

        context?.contentResolver?.notifyChange(DatabaseContract.TvColumns.CONTENT_URI_TV, null)

        return deleted
    }

}