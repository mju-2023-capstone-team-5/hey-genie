package org.sopar.presentation.pay

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import org.sopar.R
import org.sopar.data.remote.request.HourlyReservationInfo
import org.sopar.data.remote.request.MonthlyReservationInfo
import org.sopar.data.remote.response.ParkingLot
import org.sopar.databinding.FragmentPayBinding
import org.sopar.domain.entity.NetworkState
import org.sopar.presentation.base.BaseErrorDialog
import org.sopar.presentation.base.BaseFragment
import org.sopar.presentation.notice.NoticeDialog
import java.text.SimpleDateFormat

@AndroidEntryPoint
class PayFragment : BaseFragment<FragmentPayBinding>(R.layout.fragment_pay) {
    private val args: PayFragmentArgs by navArgs()
    private val payViewModel by viewModels<PayViewModel>()
    private lateinit var parkingLot: ParkingLot
    private var monthlyReservationInfo: MonthlyReservationInfo? = null
    private var hourlyReservationInfo: HourlyReservationInfo? = null
    private var price: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUp()
        setObserve()
        setBtnListener()
    }

    private fun setObserve() {
        payViewModel.reservationStatus.observe(viewLifecycleOwner) { status ->
            if (status == NetworkState.SUCCESS) {
                Toast.makeText(context, "예약이 정상 등록되었습니다!🙌", Toast.LENGTH_SHORT).show()
                val action = PayFragmentDirections.actionPayFragmentToFragmentMap()
                findNavController().navigate(action)
            } else if (status == NetworkState.FAIL) {
                val dialog = BaseErrorDialog(R.string.reservation_error)
                dialog.show(requireActivity().supportFragmentManager, "RegisterReservationError")
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