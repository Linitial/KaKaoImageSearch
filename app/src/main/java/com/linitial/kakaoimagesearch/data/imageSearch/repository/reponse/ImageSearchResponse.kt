package com.linitial.kakaoimagesearch.data.imageSearch.repository.reponse

import com.google.gson.annotations.SerializedName

data class ImageSearchResponse(

    @SerializedName("meta")
    val metaData: MetaData?,

    @SerializedName("documents")
    var imageInfoList: MutableList<ImageInfo>?
)
