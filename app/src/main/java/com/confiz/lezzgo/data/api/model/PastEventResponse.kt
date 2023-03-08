package com.confiz.lezzgo.data.api.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PastEventResponse(
    val `data`: List<Data>,
    val status: Status
) {
    @JsonClass(generateAdapter = true)
    data class Data(
        val attendees: List<String>,
        val details: String,
        val endDate: EndDate,
        val `for`: List<String>,
        val gallery: Gallery,
        val highlight: String,
        val id: String,
        val location: Location,
        val organizers: String,
        val register: String,
        val social: Social,
        val startDate: StartDate,
        val thumbnail: String,
        val title: String
    ) {
        @JsonClass(generateAdapter = true)
        data class EndDate(
            val _nanoseconds: Int,
            val _seconds: Int
        )

        @JsonClass(generateAdapter = true)
        data class Gallery(
            val media: List<String>,
            val more: String
        )

        @JsonClass(generateAdapter = true)
        data class Location(
            val address: String,
            val city: String,
            val lat: Double,
            val long: Double
        )

        @JsonClass(generateAdapter = true)
        data class Social(
            val facebook: String,
            val instagram: String,
            val linkedin: String,
            val youtube: String
        )

        @JsonClass(generateAdapter = true)
        data class StartDate(
            val _nanoseconds: Int,
            val _seconds: Int
        )
    }

    @JsonClass(generateAdapter = true)
    data class Status(
        val code: Int,
        val isSuccessful: Boolean,
        val message: String
    )
}