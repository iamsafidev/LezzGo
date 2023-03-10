package com.confiz.lezzgo.presentation.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.confiz.lezzgo.R
import com.confiz.lezzgo.databinding.FragmentDashboardBinding
import com.confiz.lezzgo.presentation.home.HomeViewModel

class DashboardFragment : Fragment() {
    private lateinit var binding: FragmentDashboardBinding
    private val homeViewModel: HomeViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container, false)
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_dashboard, container, false)
        return binding.root
    }

    fun hideProfileView() {
        binding.containerWelcome.visibility = View.GONE
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        }
        setupObservers()
    }

    private fun setupObservers() {
        homeViewModel.logoutLiveData.observe(viewLifecycleOwner) { isAllowed ->
            if (isAdded && isAllowed) {
                val navOptions: NavOptions =
                    NavOptions.Builder().setPopUpTo(R.id.loginFragment, true).build()
                findNavController().navigate(DashboardFragmentDirections.actionDashboardFragmentToLoginFragment())
                homeViewModel.clearLogoutSession()
            }
        }
    }

    private fun setupBottomNavigationBar() {
        binding.profileName.text = HtmlCompat.fromHtml(
            getString(R.string.some_text, "Sumita Zahra"),
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.dashboard_fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.dashboardBottomNav, navController)
        val navGraph = navController.navInflater.inflate(R.navigation.bottom_nav_graph)
    }
}