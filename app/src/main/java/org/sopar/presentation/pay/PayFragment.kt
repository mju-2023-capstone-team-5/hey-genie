package org.sopar.presentation.pay

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import org.sopar.R
import org.sopar.databinding.FragmentPayBinding
import org.sopar.presentation.base.BaseFragment
import java.text.SimpleDateFormat

class PayFragment : BaseFragment<FragmentPayBinding>(R.layout.fragment_pay) {
    private val args: PayFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUp()
    }

    private fun setUp() {
        val parkingLot = args.parkingLot
        val monthlyReservationInfo = args.monthlyReservationInfo
        val hourlyReservationInfo = args.hourlyReservationInfo
        val price = args.price
        val simpleDateFormat = SimpleDateFormat("yyyy년 MM월 dd일")
        binding.textParkingLotName.text = parkingLot.name
        binding.textParkingLotAddress.text = parkingLot.address
        parkingLot.imageUrl?.let {
            Glide.with(requireContext()).load(it).into(binding.imageParkingLot)
        }
        monthlyReservationInfo?.let {
            binding.textReservationDate.text = simpleDateFormat.format(it.date)
            binding.textMonthDuration .text = it.duration.toString()
            binding.monthlyReservationInfo.visibility = View.VISIBLE
        }
        hourlyReservationInfo?.let {
            binding.textReservationDate.text = simpleDateFormat.format(it.date)
            val times = it.duration.sorted()
            if (times[0] != times[times.size -1]) {
                binding.textTimeDuration.text = "${times[0]} - ${times[times.size - 1]}"
            } else {
                binding.textTimeDuration.text = times[0].toString()
            }

            binding.hourlyReservationInfo.visibility = View.VISIBLE
        }

        binding.btnReservationComplete.text = "${price}원 결제하기"
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPayBinding {
        return FragmentPayBinding.inflate(inflater, container, false)
    }

}