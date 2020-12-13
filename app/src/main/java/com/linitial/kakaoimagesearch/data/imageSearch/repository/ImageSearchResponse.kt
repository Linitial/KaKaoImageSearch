package com.linitial.kakaoimagesearch.data.imageSearch.repository

import com.google.gson.annotations.SerializedName

data class ImageSearchResponse(

    @SerializedName("meta")
    val metaData: MetaData?,

    @SerializedName("documents")
    var imageInfoList: MutableList<ImageInfo>?
)
