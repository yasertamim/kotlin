package com.example.prosjektoppgave1

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Todo (
    val item_title: String,
    var isChecked: Boolean = false
        ):Parcelable
{

}

