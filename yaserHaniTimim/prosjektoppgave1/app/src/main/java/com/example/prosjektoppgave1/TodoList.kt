package com.example.prosjektoppgave1

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class TodoList (
    var list_title: String?
        ): Parcelable