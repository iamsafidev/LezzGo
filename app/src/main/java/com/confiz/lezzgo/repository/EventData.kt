package com.confiz.lezzgo.repository

import arrow.core.Either
import arrow.core.flatten
import com.confiz.lezzgo.data.api.EventApi
import com.confiz.lezzgo.data.api.model.PastEventResponse
import com.confiz.lezzgo.data.api.model.SearchFilterRequest
import com.confiz.lezzgo.data.api.model.SearchFilterResponse
import com.confiz.lezzgo.data.api.model.SingleEventDetailResponse
import com.confiz.lezzgo.data.network.NetworkException
import com.confiz.lezzgo.data.network.NetworkHandler
import com.confiz.lezzgo.data.wrapper.either
import javax.inject.Inject

class EventData @Inject constructor(
    private val networkHandler: NetworkHandler,
    private val service: EventApi,
) : EventRepository {
    override fun searchEventByFilters(filterRequest: SearchFilterRequest): Either<Exception, SearchFilterResponse> {
        return Either.conditionally(
            networkHandler.isConnected,
            ifFalse = { NetworkException.Default },
            ifTrue = { service.searchFilterEvent(filterRequest).either() }
        ).flatten()
    }

    override fun getUpcomingEvent(): Either<Exception, SearchFilterResponse> {
        return Either.conditionally(
            networkHandler.isConnected,
            ifFalse = { NetworkException.Default },
            ifTrue = { service.getUpComingEvent().either() }
        ).flatten()
    }

    override fun getTodayEvent(): Either<Exception, SearchFilterResponse> {
        return Either.conditionally(
            networkHandler.isConnected,
            ifFalse = { NetworkException.Default },
            ifTrue = { service.getTodayEvent().either() }
        ).flatten()
    }

    override fun getPastEvent(): Either<Exception, PastEventResponse> {
        return Either.conditionally(
            networkHandler.isConnected,
            ifFalse = { NetworkException.Default },
            ifTrue = { service.getPastEvent().either() }
        ).flatten()
    }

    override fun getEventDetail(eventId:String): Either<Exception, SingleEventDetailResponse> {
        return Either.conditionally(
            networkHandler.isConnected,
            ifFalse = { NetworkException.Default },
            ifTrue = { service.getEventDetails(eventId).either() }
        ).flatten()
    }
}