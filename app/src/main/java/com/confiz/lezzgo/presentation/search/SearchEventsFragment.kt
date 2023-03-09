package com.confiz.lezzgo.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.confiz.lezzgo.R
import com.confiz.lezzgo.databinding.FragmentSearchEventsBinding
import com.confiz.lezzgo.presentation.home.HomeViewModel
import com.confiz.lezzgo.presentation.search.adapter.SearchEventAdapter

class SearchEventsFragment : Fragment() {
    lateinit var binding: FragmentSearchEventsBinding

    private val homeViewModel: HomeViewModel by activityViewModels()

    private val eventClickListener: (String) -> Unit = { eventId ->
        if (isAdded)
            findNavController()
                .navigate(
                    SearchEventsFragmentDirections.actionSearchEventsFragmentToEventDetailFragment(
                        eventId
                    )
                )
    }

    private val searchEventAdapter: SearchEventAdapter by lazy {
        SearchEventAdapter(eventClickListener)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate<FragmentSearchEventsBinding>(
            inflater,
            R.layout.fragment_search_events,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() {
        binding.tvSearchEvent.text =
            getString(R.string.filter_event, homeViewModel.searchEventLiveData.value?.data!!.size)
        binding.filterEventRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = searchEventAdapter
            searchEventAdapter.items = homeViewModel.searchEventLiveData.value?.data!!
            homeViewModel.clearSearchFilterResults()
        }

    }
}
