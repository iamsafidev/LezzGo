package com.confiz.lezzgo.presentation.home.adapters

import android.os.Build
import com.confiz.lezzgo.BR
import com.confiz.lezzgo.R
import com.confiz.lezzgo.data.api.model.Data
import com.confiz.lezzgo.databinding.SliderAdapterBinding
import com.confiz.lezzgo.presentation.base.BaseRecyclerViewAdapter
import com.confiz.lezzgo.utils.getDate


class TodayEventPagerAdapter : BaseRecyclerViewAdapter<Data, SliderAdapterBinding>() {
    override fun getLayout(): Int {
        return R.layout.slider_adapter
    }


    override fun onBindViewHolder(
        holder: Companion.BaseViewHolder<SliderAdapterBinding>,
        position: Int
    ) {
        val items = items[position]
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.binding.textViewSubTitleSliderAdapter.text =
                getDate(items.startDate._seconds, items.startDate._nanoseconds)
        }
        holder.binding.setVariable(BR.eventDetail, items)
        holder.binding.executePendingBindings()
    }
}