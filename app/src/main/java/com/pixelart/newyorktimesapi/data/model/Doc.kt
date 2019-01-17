package com.pixelart.newyorktimesapi.data.model

import com.google.gson.annotations.SerializedName

 data class Doc(
    @SerializedName("web_url") val webUrl: String = "",
    val snippet: String = "",
    val abstract: String = "",
    @SerializedName("print_page") val printPage: String = "",
    val blog: Blog = Blog(),
    val source: String = "",
    val multimedia: List<Multimedium> = emptyList(),
    val headline: Headline = Headline(),
    val keywords: List<Keyword> = emptyList(),
    @SerializedName("pub_date") val pubDate: String = "",
    @SerializedName("document_type") val documentType: String = "",
    val byline: Byline = Byline(),
    @SerializedName("type_of_material") val typeOfMaterial: String = "",
    @SerializedName("_id") val id: String = "",
    @SerializedName("word_count") val wordCount: Int = 0,
    val score: Float = 0F
)