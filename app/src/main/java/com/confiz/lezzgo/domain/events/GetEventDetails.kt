package com.confiz.lezzgo.domain.events

import arrow.core.Either
import com.confiz.lezzgo.data.api.model.SingleEventDetailResponse
import com.confiz.lezzgo.domain.interactor.UseCase
import com.confiz.lezzgo.repository.EventRepository
import javax.inject.Inject

class GetEventDetails @Inject constructor(
    private val repository: EventRepository
) : UseCase<String, SingleEventDetailResponse>() {

    override suspend fun execute(arg: String): Either<Exception, SingleEventDetailResponse> {
        return repository.getEventDetail(arg)
    }
}