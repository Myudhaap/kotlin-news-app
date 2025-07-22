package dev.mayutama.project.testnewsapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    val id: Int,
    val code: String,
    val name: String
): Parcelable
