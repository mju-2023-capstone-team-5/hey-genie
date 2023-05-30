package org.sopar.presentation.pay

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import org.sopar.R
import org.sopar.data.remote.request.HourlyReservation
import org.sopar.data.remote.request.MonthlyReservation
import org.sopar.data.remote.response.ParkingLot
import org.sopar.databinding.FragmentPayBinding
import org.sopar.domain.entity.NetworkState
import org.sopar.domain.entity.ParkingLotState
import org.sopar.presentation.base.BaseErrorDialog
import org.sopar.presentation.base.BaseFragment
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@AndroidEntryPoint
class PayFragment : BaseFragment<FragmentPayBinding>(R.layout.fragment_pay) {
    private val args: PayFragmentArgs by navArgs()
    private val payViewModel by viewModels<PayViewModel>()
    private lateinit var parkingLot: ParkingLot
    private var monthlyReservationInfo: MonthlyReservation? = null
    private var hourlyReservationInfo: HourlyReservation? = null
    private var price: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUp()
        setObserve()
        setBtnListener()
    }

    private fun setObserve() {
        payViewModel.reservationState.observe(viewLifecycleOwner) { state ->
            if (state == NetworkState.SUCCESS) {
                val dialog = BaseErrorDialog(R.string.reservation_complete)
                dialog.show(requireActivity().supportFragmentManager, "ReservationCompleteDialog")
                val action = PayFragmentDirections.actionPayFragmentToFragmentMap()
                findNavController().navigate(action)
            } else if (state == NetworkState.FAIL) {
                val dialog = BaseErrorDialog(R.string.reservation_error)
                dialog.show(requireActivity().supportFragmentManager, "RegisterReservationError")
            }
        }

        payViewModel.parkingLotState.observe(viewLifecycleOwner) { state ->
            if (state == ParkingLotState.IMPOSSIBLE) {
                val dialog = BaseErrorDialog(R.string.parkingLot_no_space)
                dialog.show(requireActivity().supportFragmentManager, "ParkingLotNoSpaceDialog")
            }
        }
    }

    private fun setBtnListener() {
        binding.btnReservationComplete.setOnClickListener {
            if (args.monthlyReservationInfo == null) {
                payViewModel.registerHourlyReservation(parkingLot.id!!, hourlyReservationInfo!!, price)
            } else {
                payViewModel.registerMonthlyReservation(parkingLot.id!!, monthlyReservationInfo!!, price)
            }
        }
    }

    private fun setUp() {
        parkingLot = args.parkingLot
        monthlyReservationInfo = args.monthlyReservationInfo
        hourlyReservationInfo = args.hourlyReservationInfo
        price = args.price

        binding.textParkingLotName.text = parkingLot.name
        binding.textParkingLotAddress.text = parkingLot.address
        parkingLot.imageUrl?.let {
            Glide.with(requireContext()).load(it).into(binding.imageParkingLot)
        }
        monthlyReservationInfo?.let {
            val date = LocalDateTime.parse(it.date, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"))
            binding.textReservationDate.text = date.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"))
            binding.textMonthDuration .text = it.duration.toString()
            binding.monthlyReservationInfo.visibility = View.VISIBLE
        }
        hourlyReservationInfo?.let {
            val date = LocalDateTime.parse(it.date, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"))
            binding.textReservationDate.text = date.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"))
            val times = it.duration.sorted()
            if (times.size > 1){
                binding.textTimeDuration.text = "${times[0]} - ${times[times.size - 1]}"
            } else {
                binding.textTimeDuration.text = times.toString()
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