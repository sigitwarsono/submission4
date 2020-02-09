package com.example.favorite

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize

data class Movie (
    var id: Int = 0,
    var title: String? = null,
    var overview: String? = null,
    var vote_average: String? = null,
    var release_date: String? = null,
    var poster: String? = null

): Parcelable

