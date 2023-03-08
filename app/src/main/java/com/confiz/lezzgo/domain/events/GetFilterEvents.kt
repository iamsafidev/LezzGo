package com.confiz.lezzgo.domain.events

import arrow.core.Either
import com.confiz.lezzgo.data.api.model.SearchFilterRequest
import com.confiz.lezzgo.data.api.model.SearchFilterResponse
import com.confiz.lezzgo.domain.interactor.UseCase
import com.confiz.lezzgo.repository.EventRepository
import javax.inject.Inject

class GetFilterEvents @Inject constructor(
    private val repository: EventRepository
) : UseCase<SearchFilterRequest, SearchFilterResponse>() {

    override suspend fun execute(arg: SearchFilterRequest): Either<Exception, SearchFilterResponse> {
        return repository.searchEventByFilters(arg)
    }
}