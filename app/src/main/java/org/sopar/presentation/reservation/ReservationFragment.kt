package org.sopar.presentation.reservation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import org.sopar.R
import org.sopar.data.remote.request.HourlyReservationInfo
import org.sopar.data.remote.request.MonthlyReservationInfo
import org.sopar.data.remote.request.Reservation
import org.sopar.data.remote.response.ParkingLot
import org.sopar.databinding.FragmentReservationBinding
import org.sopar.domain.entity.NetworkState
import org.sopar.presentation.base.BaseErrorDialog
import org.sopar.presentation.base.BaseFragment
import org.sopar.presentation.notice.NoticeDialog
import java.text.SimpleDateFormat
import java.util.Locale

@AndroidEntryPoint
class ReservationFragment : BaseFragment<FragmentReservationBinding>(R.layout.fragment_reservation) {
    private val args: ReservationFragmentArgs by navArgs()
    private val reservationViewModel by viewModels<ReservationViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObsever()
        setUp()
        setNumberPicker()
        setCompleteBtnListener()
        setDurationPickerListener()
    }

    private fun setObsever() {
        reservationViewModel.parkingLot.observe(viewLifecycleOwner) { parkingLot ->
            binding.textParkingLotName.text = parkingLot.name
            binding.textParkingLotAddress.text = parkingLot.address

            if (args.isHourly) {
                binding.textMinCost.text = parkingLot.hourly!!.minimum.toString()
                binding.textSurcharge.text = parkingLot.hourly!!.surcharge.toString()
                binding.layoutHourly.visibility = View.VISIBLE
                binding.textHourlySurcharge.visibility = View.VISIBLE
            } else {
                binding.textMinCost.text = parkingLot.monthly!!.minimum.toString()
                binding.textSurcharge.text = parkingLot.monthly!!.surcharge.toString()
                binding.layoutMonthly.visibility = View.VISIBLE
                binding.textMonthlySurcharge.visibility = View.VISIBLE
            }
        }

        reservationViewModel.getParkingLotState.observe(viewLifecycleOwner) { state ->
            if (state == NetworkState.FAIL) {
                val dialog = BaseErrorDialog(R.string.base_error)
                dialog.show(requireActivity().supportFragmentManager, "GetParkingLotError")
            }

        }
    }
    
    private fun setDurationPickerListener() {

//         binding.monthlyDurationPicker.setOnValueChangedListener { _, _, newVal ->
//             val price: Int = if (args.isHourly) {
//                 reservationViewModel.parkingLot.value!!.hourly!!.surcharge * newVal
//             } else {
//                 reservationViewModel.parkingLot.value!!.monthly!!.surcharge * newVal
//             }

//             binding.btnReservationComplete.text = "${price}원 결제하기"
//         }

//         binding.timeDurationPicker.setOnValueChangedListener { _, _, newVal ->
//             val price: Int = if (args.isHourly) {
//                 reservationViewModel.parkingLot.value!!.hourly!!.surcharge * newVal
//             } else {
//                 reservationViewModel.parkingLot.value!!.monthly!!.surcharge * newVal
//             }
//             binding.btnReservationComplete.text = "${price}원 결제하기"
//         }
    }

    private fun setCompleteBtnListener() {
        val parkingLot = reservationViewModel.parkingLot
        binding.btnReservationComplete.setOnClickListener {
            val price: Int
            val reservation: Reservation
            val minimum: Int

            if (args.isHourly) {
                val dateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
                val year = binding.hourlyDatePicker.year
                val month = binding.hourlyDatePicker.month
                val day = binding.hourlyDatePicker.dayOfMonth
                val startHour = binding.timePicker.hour
                val startMinute = binding.timePicker.minute
                val duration = binding.timeDurationPicker.value
                val date = dateFormat.parse("${year}.${month}.${day}")!!
                val hourlyReservationInfo = HourlyReservationInfo(date, startHour, startMinute, duration)

//                 minimum = parkingLot.value!!.hourly!!.minimum
//                 price = parkingLot.value!!.hourly!!.surcharge * duration
//                 reservation = Reservation(0, 0, 0, null, hourlyReservationInfo, price)

            } else {
                val dateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
                val year = binding.monthlyDatePicker.year
                val month = binding.monthlyDatePicker.month
                val day = binding.monthlyDatePicker.dayOfMonth
                val duration = binding.monthlyDurationPicker.value
                val date = dateFormat.parse("${year}.${month}.${day}")!!
                val monthlyReservationInfo = MonthlyReservationInfo(date, duration)

//                 minimum = parkingLot.value!!.monthly!!.minimum
//                 price = parkingLot.value!!.monthly!!.surcharge * duration
//                 reservation = Reservation(0, 0 ,0, monthlyReservationInfo, null, price)
            }

//             if (price >= minimum) {
//                 val action = ReservationFragmentDirections.actionReservationFragmentToPayFragment(reservation, parkingLot.value!!)
//                 findNavController().navigate(action)
//             } else {
//                 Toast.makeText(requireContext(), "최소 금액을 채워주세요!🙏", Toast.LENGTH_SHORT).show()
//             }
        }
    }

    private fun setNumberPicker() {
        binding.timeDurationPicker.apply {
            minValue = 0
            maxValue = 12
            value = 0
        }

        binding.monthlyDurationPicker.apply {
            minValue = 0
            maxValue = 12
            value = 0
        }
    }

    private fun setUp() {
        binding.timePicker.setIs24HourView(true)

        if (args.parkingLot != null) {
            reservationViewModel.setParkingLot(args.parkingLot!!)
        } else {
            reservationViewModel.getParkingLotsById(args.parkingLotId)
        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentReservationBinding {
        return FragmentReservationBinding.inflate(inflater, container, false)
    }
}