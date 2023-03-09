package com.confiz.lezzgo.presentation.filters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.confiz.lezzgo.R
import com.confiz.lezzgo.databinding.FragmentFilterBinding
import com.confiz.lezzgo.presentation.home.EventType
import com.confiz.lezzgo.presentation.home.HomeViewModel

class FilterFragment : Fragment() {
    lateinit var binding: FragmentFilterBinding
    private val homeViewModel: HomeViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate<FragmentFilterBinding>(
            inflater,
            R.layout.fragment_filter,
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
        with(binding) {
            btnApplyFilters.setOnClickListener {
                homeViewModel.apply {
                    closeNavigationDrawer()
                    searchEvent(EventType.UPCOMING)
                }
            }

            btnClear.setOnClickListener {
                resetLocationFilter()
                resetTailoredToFilter()
                homeViewModel.apply {
                    locationFilterList = ArrayList()
                    sortBy = ""
                    closeNavigationDrawer()
                }
            }

            checkboxLhr.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked){
                    if (!homeViewModel.locationFilterList.contains(
                            checkboxLhr.text.toString().trim().lowercase()
                        )
                    )
                        homeViewModel.locationFilterList.add(
                            checkboxLhr.text.toString().trim().lowercase()
                        )
                }
                else
                    homeViewModel.locationFilterList.remove(
                        checkboxLhr.text.toString().trim().lowercase()
                    )
            }

            checkboxUs.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    if (!homeViewModel.locationFilterList.contains(
                            checkboxUs.text.toString().trim().lowercase()
                        )
                    )
                        homeViewModel.locationFilterList.add(
                            checkboxUs.text.toString().trim().lowercase()
                        )
                } else
                    homeViewModel.locationFilterList.remove(
                        checkboxUs.text.toString().trim().lowercase()
                    )
            }

            checkboxIsl.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    if (!homeViewModel.locationFilterList.contains(
                            checkboxIsl.text.toString().trim().lowercase()
                        )
                    ) {
                        homeViewModel.locationFilterList.add(
                            checkboxIsl.text.toString().trim().lowercase()
                        )
                    }
                } else
                    homeViewModel.locationFilterList.remove(
                        checkboxIsl.text.toString().trim().lowercase()
                    )
            }

            radioFilter.setOnCheckedChangeListener { _, radioButtonView ->
                homeViewModel.sortBy = when (radioButtonView) {
                    R.id.upcoming -> "upcoming"
                    R.id.next_month -> "next_month"
                    R.id.most_recent -> "most_recent"
                    else -> ""
                }
            }
        }
    }

    private fun resetTailoredToFilter() {
        with(binding) {
            radioFilter.clearCheck()
        }
    }

    private fun resetLocationFilter() {
        with(binding) {
            checkboxIsl.isChecked = false
            checkboxUs.isChecked = false
            checkboxLhr.isChecked = false
        }
    }
}