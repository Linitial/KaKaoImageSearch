package com.linitial.kakaoimagesearch.data.imageSearch.repository.reponse

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

data class ImageInfo(
    @SerializedName("thumbnail_url")
    val thumbNailUrl: String?,

    @SerializedName("image_url")
    val imageUrl: String?,

    @SerializedName("display_sitename")
    val displaySiteName: String?,

    @SerializedName("datetime")
    val dateTime: Date?
) : Serializable
