package com.pixelart.newyorktimesapi.data.model

import com.google.gson.annotations.SerializedName

data class Headline(
    val main: String = "",
    val kicker: Any = "",
    @SerializedName("content_kicker") val contentKicker: Any = "",
    @SerializedName("print_headline") val printHeadline: Any = "",
    val name: Any = "",
    val seo: Any = "",
    val sub: Any = ""
)