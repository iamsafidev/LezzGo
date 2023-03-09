package com.confiz.lezzgo.presentation.profile

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
import com.confiz.lezzgo.databinding.FragmentProfileBinding
import com.confiz.lezzgo.presentation.home.HomeViewModel
import com.confiz.lezzgo.presentation.model.Result
import com.confiz.lezzgo.presentation.profile.adapter.AttendedEventAdapter
import com.confiz.lezzgo.utils.showFigFolderAlertDialog

class ProfileFragment : Fragment() {

    lateinit var binding: FragmentProfileBinding

    private val homeViewModel: HomeViewModel by activityViewModels()

    private val attendedEventAdapter: AttendedEventAdapter by lazy {
        AttendedEventAdapter {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        return binding.root
    }

    private fun setupObservers() {
        homeViewModel.pastEventLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Loading -> Unit
                is Result.Success -> attendedEventAdapter.items = it.data?.data!!
                is Result.Error -> requireContext().showFigFolderAlertDialog(message = it.exception.message)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupObservers()
        homeViewModel.getPastEvents()
    }

    private fun setupUI() {
        binding.attendedEventsRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = attendedEventAdapter
        }

        binding.btnSignOut.setOnClickListener {
            homeViewModel.logoutUser()
        }
    }

}