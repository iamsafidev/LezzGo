package com.confiz.lezzgo.data.api.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SingleEventDetailResponse(
    val data: Data,
    val status: Status
) {
    @JsonClass(generateAdapter = true)
    data class Data(
        val attendees: List<String>,
        val details: String,
        val endDate: EndDate,
        @Json(name = "for")
        val cityList: List<String>,
        val happened: Boolean,
        val id: String,
        val location: Location,
        val organizers: String,
        val startDate: StartDate,
        val thumbnail: String,
        val title: String
    ) {
        @JsonClass(generateAdapter = true)
        data class EndDate(
            val _nanoseconds: Long,
            val _seconds: Long
        )
        @JsonClass(generateAdapter = true)
        data class Location(
            val address: String,
            val city: String,
            val lat: Double,
            val long: Double
        )

        @JsonClass(generateAdapter = true)
        data class StartDate(
            val _nanoseconds: Long,
            val _seconds: Long
        )
    }
    @JsonClass(generateAdapter = true)
    data class Status(
        val code: Int,
        val isSuccessful: Boolean,
        val message: String
    )
}