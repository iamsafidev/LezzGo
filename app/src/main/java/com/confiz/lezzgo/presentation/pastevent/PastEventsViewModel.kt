package com.confiz.lezzgo.presentation.pastevent

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.confiz.lezzgo.data.api.model.PastEventResponse
import com.confiz.lezzgo.domain.events.GetPastEvents
import com.confiz.lezzgo.presentation.model.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PastEventsViewModel @Inject constructor(
    private val getPastEvents: GetPastEvents
) : ViewModel() {
    private val _pastEventLiveData: MutableLiveData<Result<PastEventResponse>> =
        MutableLiveData()
    val pastEventLiveData: LiveData<Result<PastEventResponse>> = _pastEventLiveData

    fun getPastEvents() {
        getPastEvents.invoke(
            Unit,
            onException = { exception -> _pastEventLiveData.postValue(Result.Error(exception)) },
            onResult = { searchFilterResponse ->
                _pastEventLiveData.postValue(Result.Success(searchFilterResponse))
            })
    }
}