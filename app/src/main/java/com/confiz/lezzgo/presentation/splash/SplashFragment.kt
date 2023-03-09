package com.confiz.lezzgo.presentation.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.confiz.lezzgo.R
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : Fragment() {
    private lateinit var splashDelayJob: Job

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        splashDelayJob = lifecycleScope.launch { delay(SPLASH_SHOW_DURATION) }
        splashDelayJob.invokeOnCompletion {
            findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToDashboardFragment())
        }
    }

    private companion object {
        const val SPLASH_SHOW_DURATION = 3000L
    }
}