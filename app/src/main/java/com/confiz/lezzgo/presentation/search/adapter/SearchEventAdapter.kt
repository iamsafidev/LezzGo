package com.confiz.lezzgo.presentation.search.adapter

import android.os.Build
import com.confiz.lezzgo.BR
import com.confiz.lezzgo.R
import com.confiz.lezzgo.data.api.model.Data
import com.confiz.lezzgo.databinding.FilterEventAdapterBinding
import com.confiz.lezzgo.presentation.base.BaseRecyclerViewAdapter
import com.confiz.lezzgo.utils.getDate
import com.confiz.lezzgo.utils.getDay
import com.confiz.lezzgo.utils.getMonthDate

class SearchEventAdapter(
    private val eventClickListener: (eventId: String) -> Unit,
) : BaseRecyclerViewAdapter<Data, FilterEventAdapterBinding>() {
    override fun getLayout(): Int {
        return R.layout.filter_event_adapter
    }


    override fun onBindViewHolder(
        holder: BaseRecyclerViewAdapter.Companion.BaseViewHolder<FilterEventAdapterBinding>,
        position: Int
    ) {
        val items = items[position]
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.binding.tvEventDate.text =
                getDate(items.startDate._seconds, items.startDate._nanoseconds)
            holder.binding.tvDay.text =
                getDay(items.startDate._seconds, items.startDate._nanoseconds)
            holder.binding.tvMonth.text =
                getMonthDate(items.startDate._seconds, items.startDate._nanoseconds)
        }
        holder.binding.root.setOnClickListener {
            eventClickListener(items.id)
        }
        holder.binding.setVariable(BR.filterEvent, items)
        holder.binding.executePendingBindings()
    }
}