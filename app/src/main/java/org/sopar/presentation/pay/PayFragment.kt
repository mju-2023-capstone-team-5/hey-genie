package org.sopar.presentation.pay

import android.os.Bundle
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
        val reservation = args.reservation
        val simpleDateFormat = SimpleDateFormat("yyyy년 MM월 dd일")
        binding.textParkingLotName.text = parkingLot.name
        binding.textParkingLotAddress.text = parkingLot.address
        parkingLot.imageUrl?.let {
            Glide.with(requireContext()).load(it).into(binding.imageParkingLot)
        }
        reservation.monthlyReservationInfo?.let {
            binding.textReservationDate.text = simpleDateFormat.format(it.date)
            binding.textMonthDuration .text = it.duration.toString()
            binding.monthlyReservationInfo.visibility = View.VISIBLE
        }
        reservation.hourlyReservationInfo?.let {
            binding.textReservationDate.text = simpleDateFormat.format(it.date)
            binding.textStartTime.text = "${it.startHour}:${it.startMinute}"
            binding.textTimeDuration.text = it.duration.toString()
            binding.hourlyReservationInfo.visibility = View.VISIBLE
        }

        binding.btnReservationComplete.text = "${reservation.price}원 결제하기"
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPayBinding {
        return FragmentPayBinding.inflate(inflater, container, false)
    }

}