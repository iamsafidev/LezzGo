package com.confiz.lezzgo.presentation.home.adapters

import android.os.Build
import com.confiz.lezzgo.BR
import com.confiz.lezzgo.R
import com.confiz.lezzgo.data.api.model.Data
import com.confiz.lezzgo.databinding.EventAdapterBinding
import com.confiz.lezzgo.presentation.base.BaseRecyclerViewAdapter
import com.confiz.lezzgo.utils.getDate
import com.confiz.lezzgo.utils.getDay
import com.confiz.lezzgo.utils.getMonthDate

class UpcomingEventAdapter : BaseRecyclerViewAdapter<Data, EventAdapterBinding>() {
    override fun getLayout(): Int {
        return R.layout.event_adapter
    }


    override fun onBindViewHolder(
        holder: Companion.BaseViewHolder<EventAdapterBinding>,
        position: Int
    ) {
        val items = items[position]
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.binding.tvEventDate.text =
                getDate(items.startDate._seconds, items.startDate._nanoseconds)
            holder.binding.tvDay.text= getDay(items.startDate._seconds, items.startDate._nanoseconds)
            holder.binding.tvMonth.text= getMonthDate(items.startDate._seconds, items.startDate._nanoseconds)
        }
        holder.binding.setVariable(BR.upcomingEvent, items)
        holder.binding.executePendingBindings()
    }
}