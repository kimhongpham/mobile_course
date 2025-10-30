package com.example.btvn_lab4

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FolderModel(
    val id: Int,
    var title: String,
    var description: String
) : Parcelable