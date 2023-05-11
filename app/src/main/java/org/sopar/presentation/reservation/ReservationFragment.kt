package org.sopar.presentation.reservation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import org.sopar.R
import org.sopar.data.remote.request.HourlyReservationInfo
import org.sopar.data.remote.request.MonthlyReservationInfo
import org.sopar.data.remote.request.Reservation
import org.sopar.databinding.FragmentReservationBinding
import org.sopar.presentation.base.BaseFragment
import java.text.SimpleDateFormat
import java.util.Locale

class ReservationFragment : BaseFragment<FragmentReservationBinding>(R.layout.fragment_reservation) {
    private val args: ReservationFragmentArgs by navArgs()
    private var price = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUp()
        setNumberPicker()
        setCompleteBtnListener()
        setDurationPickerListener()
    }
    
    private fun setDurationPickerListener() {
        binding.monthlyDurationPicker.setOnValueChangedListener { _, _, newVal ->
            price = args.parkingLot.surcharge ?: 0 * newVal
            binding.btnReservationComplete.text = "${price}원 결제하기"
        }

        binding.timeDurationPicker.setOnValueChangedListener { _, _, newVal ->
            price = args.parkingLot.surcharge ?: 0 * newVal
            binding.btnReservationComplete.text = "${price}원 결제하기"
        }
    }

    private fun setCompleteBtnListener() {
        val parkingLot = args.parkingLot
        binding.btnReservationComplete.setOnClickListener {
            val price: Int
            val reservation: Reservation

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

                price = parkingLot.surcharge ?: 0 * duration
                reservation = Reservation(0, 0, 0, null, hourlyReservationInfo, price)
            } else {
                val dateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
                val year = binding.monthlyDatePicker.year
                val month = binding.monthlyDatePicker.month
                val day = binding.monthlyDatePicker.dayOfMonth
                val duration = binding.monthlyDurationPicker.value
                val date = dateFormat.parse("${year}.${month}.${day}")!!
                val monthlyReservationInfo = MonthlyReservationInfo(date, duration)

                price = parkingLot.surcharge ?: 0 * duration
                reservation = Reservation(0, 0 ,0, monthlyReservationInfo, null, price)
            }

            if (price >= parkingLot.minimum ?: 0) {
                val action = ReservationFragmentDirections.actionReservationFragmentToPayFragment(reservation, parkingLot)
                findNavController().navigate(action)
            } else {
                Toast.makeText(requireContext(), "최소 금액을 채워주세요!🙏", Toast.LENGTH_SHORT).show()
            }
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

        val parkingLot = args.parkingLot
        binding.textParkingLotName.text = parkingLot.name
        binding.textParkingLotAddress.text = parkingLot.address
        binding.textMinCost.text = parkingLot.minimum.toString()
        //월/시간 단위 분리 해야 함
        binding.textSurcharge.text = parkingLot.surcharge.toString()

        if (args.isHourly) {
            binding.layoutHourly.visibility = View.VISIBLE
            binding.textHourlySurcharge.visibility = View.VISIBLE
        } else {
            binding.layoutMonthly.visibility = View.VISIBLE
            binding.textMonthlySurcharge.visibility = View.VISIBLE
        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentReservationBinding {
        return FragmentReservationBinding.inflate(inflater, container, false)
    }
}