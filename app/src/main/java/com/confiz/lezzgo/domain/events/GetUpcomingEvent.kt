package com.confiz.lezzgo.domain.events

import arrow.core.Either
import com.confiz.lezzgo.data.api.model.SearchFilterResponse
import com.confiz.lezzgo.domain.interactor.UseCase
import com.confiz.lezzgo.repository.EventRepository
import javax.inject.Inject

class GetUpcomingEvent @Inject constructor(
    private val repository: EventRepository
) : UseCase<Unit, SearchFilterResponse>() {

    override suspend fun execute(arg: Unit): Either<Exception, SearchFilterResponse> {
        return repository.getUpcomingEvent()
    }
}