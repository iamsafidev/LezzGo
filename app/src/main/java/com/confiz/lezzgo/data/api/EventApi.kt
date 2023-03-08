package com.confiz.lezzgo.data.api

import com.confiz.lezzgo.data.api.model.PastEventResponse
import com.confiz.lezzgo.data.api.model.SearchFilterRequest
import com.confiz.lezzgo.data.api.model.SearchFilterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface EventApi {
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST("api/events/filter")
    fun searchFilterEvent(@Body request: SearchFilterRequest):Call<SearchFilterResponse>


    @Headers("Content-Type: application/x-www-form-urlencoded")
    @GET("api/events/upcoming")
    fun getUpComingEvent():Call<SearchFilterResponse>

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @GET("api/events/today")
    fun getTodayEvent():Call<SearchFilterResponse>

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @GET("api/events/recent")
    fun getPastEvent():Call<PastEventResponse>

}