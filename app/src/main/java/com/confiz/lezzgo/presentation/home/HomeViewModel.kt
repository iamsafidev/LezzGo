package com.confiz.lezzgo.presentation.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.confiz.lezzgo.data.api.model.Filter
import com.confiz.lezzgo.data.api.model.SearchFilterRequest
import com.confiz.lezzgo.data.api.model.SearchFilterResponse
import com.confiz.lezzgo.domain.events.GetFilterEvents
import com.confiz.lezzgo.domain.events.GetTodayEvents
import com.confiz.lezzgo.domain.events.GetUpcomingEvent
import com.confiz.lezzgo.presentation.model.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getFilterEvents: GetFilterEvents,
    private val getUpcomingEvent: GetUpcomingEvent,
    private val getTodayEvents: GetTodayEvents
) : ViewModel() {

    var locationFilterList: ArrayList<String> = ArrayList()
    var sortBy: String = ""
    var eventSearchText: String = ""

    private val _navigationDrawerStateLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val navigationDrawerStateLiveData: LiveData<Boolean> = _navigationDrawerStateLiveData

    private val _onErrorLiveData: MutableLiveData<Exception> = MutableLiveData()
    val onErrorLiveData: LiveData<Exception> = _onErrorLiveData

    private val _upcomingEventLiveData: MutableLiveData<Result<SearchFilterResponse>> =
        MutableLiveData()
    val upcomingEventLiveData: LiveData<Result<SearchFilterResponse>> = _upcomingEventLiveData

    private val _todayEventLiveData: MutableLiveData<Result<SearchFilterResponse>> =
        MutableLiveData()
    val todayEventLiveData: LiveData<Result<SearchFilterResponse>> = _todayEventLiveData
    fun closeNavigationDrawer() {
        _navigationDrawerStateLiveData.postValue(true)
    }

    private val onError: (Exception) -> Unit = { exception ->
        _onErrorLiveData.postValue(exception)
    }

    fun getUpcomingEvent() {
        getUpcomingEvent.invoke(
            Unit,
            onException = { exception -> _upcomingEventLiveData.postValue(Result.Error(exception)) },
            onResult = { searchFilterResponse ->
                _upcomingEventLiveData.postValue(Result.Success(searchFilterResponse))
            })
    }

    fun getTodayEvent() {
        getTodayEvents.invoke(
            Unit,
            onException = { exception -> _todayEventLiveData.postValue(Result.Error(exception)) },
            onResult = { searchFilterResponse ->
                _todayEventLiveData.postValue(Result.Success(searchFilterResponse))
            })
    }


    fun searchEvent(eventType: EventType) {
        getFilterEvents.invoke(
            SearchFilterRequest(
                Filter(locationFilterList, sortBy),
                eventSearchText,
                eventType.value
            ), onError
        ) {
            Log.i("check", "success: $it")
        }

    }
}

enum class EventType(val value: String) {
    UPCOMING("upcoming"),
    PAST("recent")
}