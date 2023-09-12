package com.tonyk.android.homeworkfragments.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class Contact(
    val id: UUID,
    val name: String,
    val surname: String,
    val phoneNumber: String
) : Parcelable
