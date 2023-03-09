package com.confiz.lezzgo.presentation.pastevent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.confiz.lezzgo.R
import com.confiz.lezzgo.databinding.FragmentPastEventsBinding
import com.confiz.lezzgo.presentation.home.HomeViewModel
import com.confiz.lezzgo.presentation.model.Result
import com.confiz.lezzgo.presentation.pastevent.adapter.PastEventAdapter
import com.confiz.lezzgo.utils.showFigFolderAlertDialog


class PastEventsFragment : Fragment() {
    lateinit var binding: FragmentPastEventsBinding

    private val homeViewModel: HomeViewModel by activityViewModels()

    private val pastEventAdapter: PastEventAdapter by lazy {
        PastEventAdapter {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate<FragmentPastEventsBinding>(
            inflater,
            R.layout.fragment_past_events,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupObservers()
        homeViewModel.getPastEvents()
    }

    private fun setupObservers() {
        homeViewModel.pastEventLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Loading -> Unit
                is Result.Success -> pastEventAdapter.items = it.data?.data!!
                is Result.Error -> requireContext().showFigFolderAlertDialog(message = it.exception.message)
            }
        }
    }

    private fun setupUI() {
        binding.pastEventRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = pastEventAdapter
        }
    }


}