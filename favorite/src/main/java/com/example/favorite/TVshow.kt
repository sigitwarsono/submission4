package com.example.favorite

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize

data class TVshow (
    var id: Int=0,
    var name: String? = null,
    var overview: String? = null,
    var origin_country: String? =null,
    var first_air_date: String? = null,
    var poster: String? = null

): Parcelable