package com.confiz.lezzgo.domain.events

import arrow.core.Either
import com.confiz.lezzgo.data.api.model.PastEventResponse
import com.confiz.lezzgo.domain.interactor.UseCase
import com.confiz.lezzgo.repository.EventRepository
import javax.inject.Inject

class GetPastEvents @Inject constructor(
    private val repository: EventRepository
) : UseCase<Unit, PastEventResponse>() {

    override suspend fun execute(arg: Unit): Either<Exception, PastEventResponse> {
        return repository.getPastEvent()
    }
}