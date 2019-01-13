package com.pixelart.newyorktimesapi.data.model

data class APIResponse(
    val status: String,
    val copyright: String,
    val response: Response
)