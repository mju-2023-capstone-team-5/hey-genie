package org.sopar.data.remote.response

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class Notice(
    @field:Json(name = "title")
    val title: String,
    @field:Json(name = "content")
    val content: String
): Parcelable
