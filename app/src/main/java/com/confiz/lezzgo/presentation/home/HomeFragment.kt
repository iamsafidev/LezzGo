package com.confiz.lezzgo.presentation.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.confiz.lezzgo.R
import com.confiz.lezzgo.data.network.NetworkException
import com.confiz.lezzgo.databinding.FragmentHomeBinding
import com.confiz.lezzgo.extensions.debounce
import com.confiz.lezzgo.presentation.home.adapters.TodayEventPagerAdapter
import com.confiz.lezzgo.presentation.home.adapters.UpcomingEventAdapter
import com.confiz.lezzgo.presentation.model.Result
import com.confiz.lezzgo.utils.showFigFolderAlertDialog

class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by activityViewModels()

    private val todayEventAdapter: TodayEventPagerAdapter by lazy {
        TodayEventPagerAdapter(eventClickListener)
    }

    private val eventClickListener: (String) -> Unit = { eventId ->
        if (isAdded)
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToEventDetailFragment(
                    eventId
                )
            )
    }

    private val upcomingEventAdapter: UpcomingEventAdapter by lazy {
        UpcomingEventAdapter(eventClickListener)
    }

    var isSideMenuClosed: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate<FragmentHomeBinding?>(
            inflater,
            R.layout.fragment_home,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupObserver()
        homeViewModel.getTodayEvent()
        homeViewModel.getUpcomingEvent()
    }

    private fun setUpViewPager() {
        with(binding.onGoingEventPager) {
            adapter = todayEventAdapter
            offscreenPageLimit = 1
        }
    }

    private fun setupObserver() {
        with(homeViewModel) {
            navigationDrawerStateLiveData.observe(viewLifecycleOwner) { _ ->
                binding.drawerLayout.closeDrawer(GravityCompat.END)
            }

            onErrorLiveData.observe(viewLifecycleOwner) { exception ->
                when (exception) {
                    is NetworkException -> requireContext().showFigFolderAlertDialog(
                        title = getString(R.string.connect_issue),
                        message = exception.message
                    )
                    else -> requireContext().showFigFolderAlertDialog(message = exception.message)
                }
            }

            upcomingEventLiveData.observe(viewLifecycleOwner) {
                when (it) {
                    is Result.Loading -> Unit
                    is Result.Success -> {
                        binding.tvUpcomingEvent.text =
                            getString(R.string.upcoming_event, it.data?.data?.size)
                        upcomingEventAdapter.items = it.data?.data!!
                    }
                    is Result.Error -> requireContext().showFigFolderAlertDialog(message = it.exception.message)
                }
            }

            todayEventLiveData.observe(viewLifecycleOwner) {
                when (it) {
                    is Result.Loading -> Unit
                    is Result.Success -> {
                        if (it.data?.data?.size == 0)
                            binding.onGoingEventText.visibility = View.VISIBLE
                        else {
                            binding.onGoingEventText.visibility = View.GONE
                            todayEventAdapter.items = it.data?.data!!
                        }
                    }
                    is Result.Error -> requireContext().showFigFolderAlertDialog(message = it.exception.message)
                }
            }
        }
    }

    private fun setupUI() {
        setUpViewPager()
        with(binding) {
            upcomingEventRecycler.apply {
                layoutManager = LinearLayoutManager(requireContext(), VERTICAL, false)
                adapter = upcomingEventAdapter
            }
            ivFilter.setOnClickListener {
                isSideMenuClosed = !isSideMenuClosed
                if (isSideMenuClosed) {
                    drawerLayout.openDrawer(GravityCompat.END)
                    drawerLayout.visibility = View.VISIBLE
                } else {
                    drawerLayout.closeDrawer(GravityCompat.END)
                    drawerLayout.visibility = View.GONE
                }

                /*if (!drawerLayout.isDrawerOpen(GravityCompat.END))
                    drawerLayout.openDrawer(GravityCompat.END)
                else
                    drawerLayout.closeDrawer(GravityCompat.END)*/
            }

            editTextHome.addTextChangedListener(object : TextWatcher {
                val debounceFunction = debounce<String>(
                    DEBOUNCE_TIME,
                    viewLifecycleOwner.lifecycleScope
                ) {
                    homeViewModel.eventSearchText = it
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun afterTextChanged(editable: Editable?) {
                    debounceFunction(editable?.trim().toString())
                }
            })

            editTextHome.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (drawerLayout.isDrawerOpen(GravityCompat.END))
                        drawerLayout.closeDrawer(GravityCompat.END)

                    homeViewModel.searchEvent(EventType.UPCOMING)
                }
                true
            }
        }
    }

    companion object {
        private const val DEBOUNCE_TIME = 400L
        private const val DEBOUNCE_MIN_CHARACTERS = 0
    }
}