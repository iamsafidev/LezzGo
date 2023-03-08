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
import com.confiz.lezzgo.R
import com.confiz.lezzgo.presentation.model.Result
import com.confiz.lezzgo.data.network.NetworkException
import com.confiz.lezzgo.databinding.FragmentHomeBinding
import com.confiz.lezzgo.extensions.debounce
import com.confiz.lezzgo.utils.showFigFolderAlertDialog

class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by activityViewModels()

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

            upcomingEventLiveData.observe(viewLifecycleOwner){
                when(it){
                    is Result.Loading -> Unit
                    is Result.Success-> Unit
                    is Result.Error -> requireContext().showFigFolderAlertDialog(message = it.exception.message)
                }
            }

            todayEventLiveData.observe(viewLifecycleOwner){
                when(it){
                    is Result.Loading -> Unit
                    is Result.Success-> Unit
                    is Result.Error -> requireContext().showFigFolderAlertDialog(message = it.exception.message)
                }
            }
        }
    }

    private fun setupUI() {
        with(binding) {
            ivFilter.setOnClickListener {
                if (!drawerLayout.isDrawerOpen(GravityCompat.END))
                    drawerLayout.openDrawer(GravityCompat.END)
                else
                    drawerLayout.closeDrawer(GravityCompat.END)
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