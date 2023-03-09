package com.confiz.lezzgo.data.api

import com.confiz.lezzgo.data.api.model.PastEventResponse
import com.confiz.lezzgo.data.api.model.SearchFilterRequest
import com.confiz.lezzgo.data.api.model.SearchFilterResponse
import com.confiz.lezzgo.data.api.model.SingleEventDetailResponse
import retrofit2.Call
import javax.inject.Inject

class EventService @Inject constructor(
    private val eventApi: EventApi
) : EventApi {
    override fun searchFilterEvent(request: SearchFilterRequest): Call<SearchFilterResponse> {
        return eventApi.searchFilterEvent(request)
    }

    override fun getUpComingEvent(): Call<SearchFilterResponse> {
        return eventApi.getUpComingEvent()
    }

    override fun getTodayEvent(): Call<SearchFilterResponse> {
        return eventApi.getTodayEvent()
    }

    override fun getPastEvent(): Call<PastEventResponse> {
        return eventApi.getPastEvent()
    }

    override fun getEventDetails(eventId: String): Call<SingleEventDetailResponse> {
        return eventApi.getEventDetails(eventId)
    }
}