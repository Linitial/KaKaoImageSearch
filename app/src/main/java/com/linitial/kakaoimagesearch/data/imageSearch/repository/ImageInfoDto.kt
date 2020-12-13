package com.linitial.kakaoimagesearch.data.imageSearch.repository

import java.util.*

data class ImageInfoDto(
    val thumbNailUrl: String,
    val imageUrl: String,
    val displaySiteName: String,
    val dateTime: Date
)
