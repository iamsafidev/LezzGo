package com.confiz.lezzgo.presentation.profile.adapter

import android.os.Build
import com.confiz.lezzgo.BR
import com.confiz.lezzgo.R
import com.confiz.lezzgo.data.api.model.PastEventResponse
import com.confiz.lezzgo.databinding.AttendedEventAdpaterBinding
import com.confiz.lezzgo.presentation.base.BaseRecyclerViewAdapter
import com.confiz.lezzgo.utils.getDate
import com.confiz.lezzgo.utils.getDateWithOutHours

class AttendedEventAdapter(
    private val eventClickListener: (eventId: String) -> Unit,
) : BaseRecyclerViewAdapter<PastEventResponse.Data, AttendedEventAdpaterBinding>() {
    override fun getLayout(): Int {
        return R.layout.attended_event_adpater
    }


    override fun onBindViewHolder(
        holder: BaseRecyclerViewAdapter.Companion.BaseViewHolder<AttendedEventAdpaterBinding>,
        position: Int
    ) {
        val items = items[position]
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.binding.textViewSubTitleSliderAdapter.text =
                getDateWithOutHours(items.startDate._seconds, items.startDate._nanoseconds)
        }
        holder.binding.root.setOnClickListener {
            eventClickListener(items.id)
        }
        holder.binding.setVariable(BR.eventDetail, items)
        holder.binding.executePendingBindings()
    }
}