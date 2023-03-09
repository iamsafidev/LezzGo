package com.confiz.lezzgo.presentation.eventDetail

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.confiz.lezzgo.R
import com.confiz.lezzgo.data.api.model.SingleEventDetailResponse
import com.confiz.lezzgo.databinding.FragmentEventDetailBinding
import com.confiz.lezzgo.presentation.home.HomeViewModel
import com.confiz.lezzgo.presentation.model.Result
import com.confiz.lezzgo.utils.getDate
import com.confiz.lezzgo.utils.getHours
import com.confiz.lezzgo.utils.showFigFolderAlertDialog

class EventDetailFragment : Fragment() {
    val args: EventDetailFragmentArgs by navArgs()
    lateinit var binding: FragmentEventDetailBinding
    var data: SingleEventDetailResponse.Data? = null
    private val homeViewModel: HomeViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate<FragmentEventDetailBinding>(
            inflater,
            R.layout.fragment_event_detail,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupObservers()
        homeViewModel.getEventDetails(args.eventId)

    }

    private fun setupUI() {
        with(binding) {
            clLocation.setOnClickListener { _ ->
                try {
                    val latitude: String = data?.location?.lat.toString()
                    val longitude: String = data?.location?.long.toString()
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?daddr=$latitude,$longitude")
                    )
                    intent.setClassName(
                        "com.google.android.apps.maps",
                        "com.google.android.maps.MapsActivity"
                    )
                    startActivity(intent)
                } catch (exception: Exception) {
                    requireContext().showFigFolderAlertDialog(message = exception.message)
                }

            }
        }
    }

    private fun setupObservers() {
        homeViewModel.eventDetailLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Loading -> Unit
                is Result.Success -> {
                    data = it.data?.data
                    setTextViewEventDate()
                    setImageThumbnail()
                    setLocation()
                    setRegisterButtonVisibility()
                    binding.eventDetail = data
                }
                is Result.Error -> requireContext().showFigFolderAlertDialog(message = it.exception.message)
            }
        }
    }

    private fun setRegisterButtonVisibility() {
        binding.btnRegister.visibility = if (data?.happened == true) View.GONE else View.VISIBLE
    }

    private fun setLocation() {
        data?.let {
            binding.tvLocation.text = "${it.location.address}, ${it.location.city}"
        }
    }

    private fun setImageThumbnail() {
        Glide.with(requireContext()).load(data?.thumbnail).centerCrop()
            .placeholder(R.drawable.ic_logo_lezzgo)
            .into(binding.imageViewEd)
    }

    private fun setTextViewEventDate() {
        data?.startDate?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                val startDate = getDate(it._seconds, it._nanoseconds)
                data?.endDate?.let { endDate ->
                    val endTime = getHours(endDate._seconds, endDate._nanoseconds)
                    binding.tvDate.text = "$startDate - $endTime"
                }
            }
        }
    }
}