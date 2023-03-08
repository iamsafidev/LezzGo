package com.confiz.lezzgo.repository

import arrow.core.Either
import com.confiz.lezzgo.data.api.model.PastEventResponse
import com.confiz.lezzgo.data.api.model.SearchFilterRequest
import com.confiz.lezzgo.data.api.model.SearchFilterResponse

interface EventRepository {
    fun searchEventByFilters(filterRequest: SearchFilterRequest): Either<Exception, SearchFilterResponse>
    fun getUpcomingEvent(): Either<Exception, SearchFilterResponse>
    fun getTodayEvent(): Either<Exception, SearchFilterResponse>
    fun getPastEvent(): Either<Exception, PastEventResponse>
}