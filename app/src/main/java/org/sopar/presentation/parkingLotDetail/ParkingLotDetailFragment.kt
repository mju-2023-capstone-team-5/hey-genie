package org.sopar.presentation.parkingLotDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import org.sopar.R
import org.sopar.databinding.FragmentParkingLotDetailBinding
import org.sopar.domain.entity.NetworkState
import org.sopar.presentation.base.BaseErrorDialog
import org.sopar.presentation.base.BaseFragment

@AndroidEntryPoint
class ParkingLotDetailFragment : BaseFragment<FragmentParkingLotDetailBinding>(R.layout.fragment_parking_lot_detail) {
    private val args by navArgs<ParkingLotDetailFragmentArgs>()
    private val parkingLotDetailViewModel by viewModels<ParkingLotDetailViewModel>()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentParkingLotDetailBinding {
        return FragmentParkingLotDetailBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        setDeleteBtnListener()
        setObserve()
    }

    private fun setObserve() {
        parkingLotDetailViewModel.deleteStatus.observe(viewLifecycleOwner) { status ->
            if (status == NetworkState.SUCCESS) {
                Toast.makeText(context, "삭제가 완료되었습니다!", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_parkingLotDetailFragment_to_fragment_my_parking_lot)
            } else if (status == NetworkState.FAIL) {
                val dialog = BaseErrorDialog(R.string.delete_parking_lot_error)
                dialog.show(requireActivity().supportFragmentManager, "DeleteParkingLotError")
            }

        }
    }

    private fun init() {
        val parkingLot = args.parkingLot

        binding.textParkingLotName.text = parkingLot.name
        binding.textParkingLotAddress.text = parkingLot.address
        binding.textParkingLotSpace.text = parkingLot.totalSpace.toString()
        parkingLot.availableTime?.let {
            val list = arrayListOf<String>()
            for (item in it) {
                list.add(item.startTime.toString())
            }

            binding.textSharingTimes.text = list.sorted().joinToString(separator = ", ")
        }
        parkingLot.availableDay?.let {
            binding.textSharingDays.text = it.joinToString(separator = ", ")
        }
        binding.textCarTypes.text = parkingLot.type?.joinToString(separator = ", ")
        binding.textPhoneNumber.text = parkingLot.phoneNumber
        parkingLot.freeInformation?.let {
            binding.textCaution.text = it
        }
        parkingLot.hourly?.let {
            binding.textHourly.text = "${it.minimum}원(최소) / ${it.surcharge}원(추가)"
        }
        parkingLot.monthly?.let {
            binding.textMonthly.text = "${it.minimum}원(최소) / ${it.surcharge}원(추가)"
        }

        parkingLot.reviewSummary?.let {
            binding.textParkingLotComment.text = it
        }
        binding.parkingLotRating.rating = parkingLot.ratingAvg!!.toFloat()
        binding.textCommentAmount.text = parkingLot.ratingNum.toString()
    }

    private fun setDeleteBtnListener() {
        binding.btnDeleteParkingLot.setOnClickListener {
            parkingLotDetailViewModel.deleteParkingLot(args.parkingLot.id!!)
        }
    }



}