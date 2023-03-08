package com.confiz.lezzgo.data.api.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchFilterRequest(
    val filter: Filter,
    val search: String,
    val type: String
)

@JsonClass(generateAdapter = true)
data class Filter(
    val location: List<String>,
    val tailored: String
)
