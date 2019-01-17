package com.pixelart.newyorktimesapi.data.model

data class Byline(
    val original: String = "",
    val person: List<Person> = emptyList(),
    val organization: Any= ""
)