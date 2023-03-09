package com.confiz.lezzgo.presentation.closefriends.adapter

import com.confiz.lezzgo.BR
import com.confiz.lezzgo.R
import com.confiz.lezzgo.data.api.model.CloseFriendsResponse
import com.confiz.lezzgo.databinding.CloseFriendsAdapterBinding
import com.confiz.lezzgo.presentation.base.BaseRecyclerViewAdapter

class CloseFriendAdapter :
    BaseRecyclerViewAdapter<CloseFriendsResponse, CloseFriendsAdapterBinding>() {
    override fun getLayout(): Int {
        return R.layout.close_friends_adapter
    }

    override fun onBindViewHolder(
        holder: BaseRecyclerViewAdapter.Companion.BaseViewHolder<CloseFriendsAdapterBinding>,
        position: Int
    ) {
        val item = items[position]
        holder.binding.setVariable(BR.friends, item)
        holder.binding.imgNotification.setOnClickListener {
            val list = items.filter {
                it != item
            }
            items = list
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, items.size)
        }
        holder.binding.executePendingBindings()
    }
}