package com.confiz.lezzgo.presentation.closefriends

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.confiz.lezzgo.R
import com.confiz.lezzgo.data.api.model.CloseFriendsResponse
import com.confiz.lezzgo.databinding.FragmentCloseFriendBinding
import com.confiz.lezzgo.presentation.closefriends.adapter.CloseFriendAdapter

class CloseFriendFragment : Fragment() {

    lateinit var binding: FragmentCloseFriendBinding

    private val closeFriendsAdapter: CloseFriendAdapter by lazy {
        CloseFriendAdapter()
    }

    private val closeFriendsList: ArrayList<CloseFriendsResponse> =
        ArrayList<CloseFriendsResponse>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_close_friend, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchCloseFriendList()
        setupUI()

    }

    private fun fetchCloseFriendList() {
        closeFriendsList.add(CloseFriendsResponse("Hira Nisar Warsi", "Software Engineer"))
        closeFriendsList.add(CloseFriendsResponse("Sadia Shahid", "Software Engineer"))
        closeFriendsList.add(CloseFriendsResponse("Aqsa Tariq", "Software Engineer"))
        closeFriendsList.add(CloseFriendsResponse("Murtaza Naqvi", "Product Manager"))
        closeFriendsList.add(CloseFriendsResponse("Muhammad Tayyab", "Software Engineer"))
        closeFriendsList.add(CloseFriendsResponse("Humail Shahzad", "Software Engineer"))
        closeFriendsList.add(CloseFriendsResponse("Safi ur Rehman", "Senior Software Engineer"))
        closeFriendsList.add(CloseFriendsResponse("Misha Mahmood", "Assistant Manager"))

    }

    private fun setupUI() {
        //binding.tvCloseFriends.text = getString(R.string.close_friends, closeFriendsList.size)
        binding.friendsRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = closeFriendsAdapter
            closeFriendsAdapter.items = closeFriendsList
        }
    }
}