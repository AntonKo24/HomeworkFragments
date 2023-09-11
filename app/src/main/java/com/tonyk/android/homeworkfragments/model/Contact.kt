package com.tonyk.android.homeworkfragments.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.UUID

@Parcelize
data class Contact (
    val id: UUID,
    var name: String,
    var surname : String,
    var phoneNumber : String
) : Parcelable
