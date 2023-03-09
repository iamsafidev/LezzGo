package com.confiz.lezzgo.presentation.eventDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.confiz.lezzgo.R
import com.confiz.lezzgo.databinding.FragmentEventDetailBinding

class EventDetailFragment : Fragment() {

    lateinit var binding: FragmentEventDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentEventDetailBinding>(
            inflater,
            R.layout.fragment_event_detail,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}