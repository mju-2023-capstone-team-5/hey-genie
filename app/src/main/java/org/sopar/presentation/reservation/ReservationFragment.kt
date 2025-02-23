package org.sopar.presentation.reservation

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint
import org.sopar.R
import org.sopar.data.remote.request.*
import org.sopar.data.remote.response.ParkingLot
import org.sopar.databinding.FragmentReservationBinding
import org.sopar.domain.entity.NetworkState
import org.sopar.presentation.base.BaseErrorDialog
import org.sopar.presentation.base.BaseFragment
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.stream.IntStream.range

@AndroidEntryPoint
class ReservationFragment : BaseFragment<FragmentReservationBinding>(R.layout.fragment_reservation) {
    private val args: ReservationFragmentArgs by navArgs()
    private val reservationViewModel by viewModels<ReservationViewModel>()
    private var checkedTime = mutableSetOf<Int>()
    private var price: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTime()
        setObsever()
        setUp()
        setCommentDialog()
        setDatePickerListener()
        setTimeListener()
        setNumberPicker()
        setCompleteBtnListener()
        setDurationPickerListener()
    }

    private fun setDatePickerListener() {
        binding.hourlyDatePicker.setOnDateChangedListener { datePicker, _, _, _ ->
            val cal = Calendar.getInstance()
            cal.set(datePicker.year, datePicker.month, datePicker.dayOfMonth)
            Log.d("cal time", cal.time.toString())
            val day = cal.get(Calendar.DAY_OF_WEEK)
            Log.d("day", day.toString())
            when(day) {
                1-> binding.textHourlyDayOfWeek.text = "일요일"
                2-> binding.textHourlyDayOfWeek.text = "월요일"
                3-> binding.textHourlyDayOfWeek.text = "화요일"
                4-> binding.textHourlyDayOfWeek.text = "수요일"
                5-> binding.textHourlyDayOfWeek.text = "목요일"
                6-> binding.textHourlyDayOfWeek.text = "금요일"
                7-> binding.textHourlyDayOfWeek.text = "토요일"
            }
        }

        binding.monthlyDatePicker.setOnDateChangedListener { datePicker, _, _, _ ->
            val cal = Calendar.getInstance()
            cal.set(datePicker.year, datePicker.month, datePicker.dayOfMonth)
            Log.d("cal time", cal.time.toString())
            val day = cal.get(Calendar.DAY_OF_WEEK)
            Log.d("day", day.toString())
            when(day) {
                1-> binding.textMonthlyDayOfWeek.text = "일요일"
                2-> binding.textMonthlyDayOfWeek.text = "월요일"
                3-> binding.textMonthlyDayOfWeek.text = "화요일"
                4-> binding.textMonthlyDayOfWeek.text = "수요일"
                5-> binding.textMonthlyDayOfWeek.text = "목요일"
                6-> binding.textMonthlyDayOfWeek.text = "금요일"
                7-> binding.textMonthlyDayOfWeek.text = "토요일"
            }
        }
    }

    private fun setCommentDialog() {
        binding.layoutComment.setOnClickListener {
            val dialog: CommentListDialog
            if (args.parkingLot != null) {
                dialog = CommentListDialog(reservationViewModel, args.parkingLot!!.id!!)
            } else {
                dialog = CommentListDialog(reservationViewModel, args.parkingLotId)
            }
            dialog.show(requireActivity().supportFragmentManager, "CommentListDialog")
        }
    }

    private fun setObsever() {
        reservationViewModel.parkingLot.observe(viewLifecycleOwner) { parkingLot ->
            setAvailableTime(parkingLot)
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

            binding.textDays.text = parkingLot.availableDay.joinToString(separator = ", ")

            parkingLot.reviewSummary?.let {
                binding.textParkingLotComment.text = it
            }
            binding.parkingLotRating.rating = parkingLot.ratingAvg!!.toFloat()
            binding.textCommentAmount.text = parkingLot.ratingNum.toString()
        }

        reservationViewModel.getParkingLotState.observe(viewLifecycleOwner) { state ->
            if (state == NetworkState.FAIL) {
                Log.d("ReservationFragment", "getparkingLotState Error")
                val dialog = BaseErrorDialog(R.string.base_error)
                dialog.show(requireActivity().supportFragmentManager, "GetParkingLotError")
            }

        }

        reservationViewModel.times.observe(viewLifecycleOwner) { times ->
            if (times.isNotEmpty()) {
                if (args.isHourly) {
                    if (checkAvailable(times.toList())) {
                        price = (reservationViewModel.parkingLot.value?.hourly?.surcharge ?: 0) * (times.size)
                        binding.btnReservationComplete.text = "${price}원 결제하기"
                    }
                } else {
                    price = (reservationViewModel.parkingLot.value?.monthly?.surcharge ?: 0) * (times.toList()[0])
                    binding.btnReservationComplete.text = "${price}원 결제하기"
                }

            }
        }
    }

    private fun checkAvailable(times: List<Int>): Boolean {
        val temp = times.sorted()
        for (i in range(1, times.size)) {
            if (temp[i] != temp[i-1]+1) {
                Toast.makeText(context, "연속된 시간을 예약해주세요!", Toast.LENGTH_SHORT).show()
                return false
            }
        }
        return true
    }
    
    private fun setDurationPickerListener() {

         binding.monthlyDurationPicker.setOnValueChangedListener { _, _, newVal ->
             reservationViewModel.postTimes(mutableSetOf(newVal))
             price = if (args.isHourly) {
                 reservationViewModel.parkingLot.value!!.hourly!!.surcharge * newVal
             } else {
                 reservationViewModel.parkingLot.value!!.monthly!!.surcharge * newVal
             }

             binding.btnReservationComplete.text = "${price}원 결제하기"
         }
    }

    private fun setTimeListener() {
        binding.timeSlotGroup1.timeBtnGroup1.addOnButtonCheckedListener { _, checkedId, isChecked ->
            when(checkedId) {
                R.id.time_0 -> {
                    if (isChecked) {
                        checkedTime.add(0)
                    } else {
                        checkedTime.remove(0)
                    }
                    reservationViewModel.postTimes(checkedTime)
                }
                R.id.time_1 -> {
                    if (isChecked) {
                        checkedTime.add(1)
                    } else {
                        checkedTime.remove(1)
                    }
                    reservationViewModel.postTimes(checkedTime)
                }
                R.id.time_2 -> {
                    if (isChecked) {
                        checkedTime.add(2)
                    } else {
                        checkedTime.remove(2)
                    }
                    reservationViewModel.postTimes(checkedTime)
                }
                R.id.time_3 -> {
                    if (isChecked) {
                        checkedTime.add(3)
                    } else {
                        checkedTime.remove(3)
                    }
                    reservationViewModel.postTimes(checkedTime)
                }


            }
        }
        binding.timeSlotGroup2.timeBtnGroup1.addOnButtonCheckedListener { _, checkedId, isChecked ->
            when(checkedId) {
                R.id.time_0 -> {
                    if (isChecked) {
                        checkedTime.add(4)
                    } else {
                        checkedTime.remove(4)
                    }
                    reservationViewModel.postTimes(checkedTime)
                }
                R.id.time_1 -> {
                    if (isChecked) {
                        checkedTime.add(5)
                    } else {
                        checkedTime.remove(5)
                    }
                    reservationViewModel.postTimes(checkedTime)
                }
                R.id.time_2 -> {
                    if (isChecked) {
                        checkedTime.add(6)
                    } else {
                        checkedTime.remove(6)
                    }
                    reservationViewModel.postTimes(checkedTime)
                }
                R.id.time_3 -> {
                    if (isChecked) {
                        checkedTime.add(7)
                    } else {
                        checkedTime.remove(7)
                    }
                    reservationViewModel.postTimes(checkedTime)
                }

            }
        }
        binding.timeSlotGroup3.timeBtnGroup1.addOnButtonCheckedListener { _, checkedId, isChecked ->
            when(checkedId) {
                R.id.time_0 -> {
                    if (isChecked) {
                        checkedTime.add(8)
                    } else {
                        checkedTime.remove(8)
                    }
                    reservationViewModel.postTimes(checkedTime)
                }
                R.id.time_1 -> {
                    if (isChecked) {
                        checkedTime.add(9)
                    } else {
                        checkedTime.remove(9)
                    }
                    reservationViewModel.postTimes(checkedTime)
                }
                R.id.time_2 -> {
                    if (isChecked) {
                        checkedTime.add(10)
                    } else {
                        checkedTime.remove(10)
                    }
                    reservationViewModel.postTimes(checkedTime)
                }
                R.id.time_3 -> {
                    if (isChecked) {
                        checkedTime.add(11)
                    } else {
                        checkedTime.remove(11)
                    }
                    reservationViewModel.postTimes(checkedTime)
                }

            }
        }
        binding.timeSlotGroup4.timeBtnGroup1.addOnButtonCheckedListener { _, checkedId, isChecked ->
            when(checkedId) {
                R.id.time_0 -> {
                    if (isChecked) {
                        checkedTime.add(12)
                    } else {
                        checkedTime.remove(12)
                    }
                    reservationViewModel.postTimes(checkedTime)
                }
                R.id.time_1 -> {
                    if (isChecked) {
                        checkedTime.add(13)
                    } else {
                        checkedTime.remove(13)
                    }
                    reservationViewModel.postTimes(checkedTime)
                }
                R.id.time_2 -> {
                    if (isChecked) {
                        checkedTime.add(14)
                    } else {
                        checkedTime.remove(14)
                    }
                    reservationViewModel.postTimes(checkedTime)
                }
                R.id.time_3 -> {
                    if (isChecked) {
                        checkedTime.add(15)
                    } else {
                        checkedTime.remove(15)
                    }
                    reservationViewModel.postTimes(checkedTime)
                }

            }
        }
        binding.timeSlotGroup5.timeBtnGroup1.addOnButtonCheckedListener { _, checkedId, isChecked ->
            when(checkedId) {
                R.id.time_0 -> {
                    if (isChecked) {
                        checkedTime.add(16)
                    } else {
                        checkedTime.remove(16)
                    }
                    reservationViewModel.postTimes(checkedTime)
                }
                R.id.time_1 -> {
                    if (isChecked) {
                        checkedTime.add(17)
                    } else {
                        checkedTime.remove(17)
                    }
                    reservationViewModel.postTimes(checkedTime)
                }
                R.id.time_2 -> {
                    if (isChecked) {
                        checkedTime.add(18)
                    } else {
                        checkedTime.remove(18)
                    }
                    reservationViewModel.postTimes(checkedTime)
                }
                R.id.time_3 -> {
                    if (isChecked) {
                        checkedTime.add(19)
                    } else {
                        checkedTime.remove(19)
                    }
                    reservationViewModel.postTimes(checkedTime)
                }

            }
        }
        binding.timeSlotGroup6.timeBtnGroup1.addOnButtonCheckedListener { _, checkedId, isChecked ->
            when(checkedId) {
                R.id.time_0 -> {
                    if (isChecked) {
                        checkedTime.add(20)
                    } else {
                        checkedTime.remove(20)
                    }
                    reservationViewModel.postTimes(checkedTime)
                }
                R.id.time_1 -> {
                    if (isChecked) {
                        checkedTime.add(21)
                    } else {
                        checkedTime.remove(21)
                    }
                    reservationViewModel.postTimes(checkedTime)
                }
                R.id.time_2 -> {
                    if (isChecked) {
                        checkedTime.add(22)
                    } else {
                        checkedTime.remove(22)
                    }
                    reservationViewModel.postTimes(checkedTime)
                }
                R.id.time_3 -> {
                    if (isChecked) {
                        checkedTime.add(23)
                    } else {
                        checkedTime.remove(23)
                    }
                    reservationViewModel.postTimes(checkedTime)
                }

            }
        }
    }

    private fun setCompleteBtnListener() {
        val parkingLot = reservationViewModel.parkingLot
        binding.btnReservationComplete.setOnClickListener {
            val times = reservationViewModel.times.value!!.toList().sorted()
            val minimum: Int
            if (times.isNotEmpty()) {
                if (checkAvailable(checkedTime.toList())) {
                    val startTime = if (times[0] < 10) {
                        "0"+times[0]
                    } else {
                        times[0]
                    }
                    if (args.isHourly) {
                        val format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                        val year = binding.hourlyDatePicker.year
                        val month = if (binding.hourlyDatePicker.month < 10) {
                            "0"+((binding.hourlyDatePicker.month+1).toString())
                        } else {
                            (binding.hourlyDatePicker.month+1).toString()
                        }
                        val day = if (binding.hourlyDatePicker.dayOfMonth < 10) {
                            "0" + binding.hourlyDatePicker.dayOfMonth
                        } else {
                            binding.hourlyDatePicker.dayOfMonth
                        }
                        val date = LocalDateTime.parse("${year}-${month}-${day} ${startTime}:00:00", format)
                        val hourlyReservationInfo = HourlyReservation(date.toString(), times)
                        minimum = parkingLot.value!!.hourly!!.minimum
                        price = parkingLot.value!!.hourly!!.surcharge * checkedTime.size
                        if (checkDay(date)) {
                            if (minimum <= price) {
                                val action = ReservationFragmentDirections.actionReservationFragmentToPayFragment(
                                    parkingLot.value!!,
                                    hourlyReservationInfo,
                                    null,
                                    price
                                )
                                findNavController().navigate(action)
                            } else {
                                Toast.makeText(requireContext(), "최소 금액을 채워주세요!🙏", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        } else {
                            Log.d("date", date.toString())
                            Toast.makeText(requireContext(), "가능한 요일을 선택해주세요!🙏", Toast.LENGTH_SHORT)
                                .show()
                        }
                    } else {
                        val format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                        val year = binding.monthlyDatePicker.year
                        val month = if (binding.monthlyDatePicker.month < 10) {
                            "0"+binding.monthlyDatePicker.month.toString()
                        } else {
                            binding.monthlyDatePicker.month.toString()
                        }
                        val day = if (binding.monthlyDatePicker.dayOfMonth < 10) {
                            "0" + binding.monthlyDatePicker.dayOfMonth
                        } else {
                            binding.monthlyDatePicker.dayOfMonth
                        }
                        val duration = binding.monthlyDurationPicker.value
                        val date = LocalDateTime.parse("${year}-${month}-${day} 00:00:00", format)
                        val monthlyReservationInfo = MonthlyReservation(date.toString(), listOf(duration))
                        minimum = parkingLot.value!!.monthly!!.minimum
                        price = parkingLot.value!!.monthly!!.surcharge * duration
                        if (checkDay(date)) {
                            if (minimum <= price) {
                                val action = ReservationFragmentDirections.actionReservationFragmentToPayFragment(
                                    parkingLot.value!!,
                                    null,
                                    monthlyReservationInfo,
                                    price
                                )
                                findNavController().navigate(action)
                            } else {
                                Toast.makeText(requireContext(), "최소 금액을 채워주세요!🙏", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        } else {
                            Toast.makeText(requireContext(), "가능한 요일을 선택해주세요!🙏", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                } else {
                    Toast.makeText(context, "가능한 시간대를 선택해주세요!🙏", Toast.LENGTH_SHORT).show()
                }
            } else {
                if (args.isHourly) {
                    Toast.makeText(context, "예약할 시간대를 알려주세요!🙏", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "대여 기간을 알려주세요!🙏", Toast.LENGTH_SHORT).show()
                }

            }

        }
    }

    private fun checkDay(date: LocalDateTime): Boolean {
        val availableDays = reservationViewModel.parkingLot.value!!.availableDay
        val cal = Calendar.getInstance()
//        cal.time = date
        cal.set(date.year, date.monthValue-1, date.dayOfMonth)
        Log.d("cal time", cal.time.toString())
        val day = cal.get(Calendar.DAY_OF_WEEK)
        Log.d("day", day.toString())
        if ((day == 1) and ("일" in availableDays)) {
            return true
        } else if ((day == 2) and ("월" in availableDays)) {
            return true
        } else if ((day == 3) and ("화" in availableDays)) {
            return true
        } else if ((day == 4) and ("수" in availableDays)) {
            return true
        } else if ((day == 5) and ("목" in availableDays)) {
            return true
        } else if ((day == 6) and ("금" in availableDays)) {
            return true
        } else if ((day == 7) and ("토" in availableDays)) {
            return true
        }
        return false
    }

    private fun setNumberPicker() {
        binding.monthlyDurationPicker.apply {
            minValue = 0
            maxValue = 12
            value = 0
        }
    }

    private fun setTime() {
        binding.timeSlotGroup1.timeBtnGroup1.apply {
            findViewById<MaterialButton>(R.id.time_0).apply {
                text = "0"
                isCheckable = false
                setTextColor(resources.getColor(R.color.base_blue))
            }
            findViewById<MaterialButton>(R.id.time_1).apply {
                text = "1"
                isCheckable = false
                setTextColor(resources.getColor(R.color.base_blue))
            }
            findViewById<MaterialButton>(R.id.time_2).apply {
                text = "2"
                isCheckable = false
                setTextColor(resources.getColor(R.color.base_blue))
            }
            findViewById<MaterialButton>(R.id.time_3).apply {
                text = "3"
                isCheckable = false
                setTextColor(resources.getColor(R.color.base_blue))
            }
        }
        binding.timeSlotGroup2.timeBtnGroup1.apply {
            findViewById<MaterialButton>(R.id.time_0).apply {
                text = "4"
                isCheckable = false
                setTextColor(resources.getColor(R.color.base_blue))
            }
            findViewById<MaterialButton>(R.id.time_1).apply {
                text = "5"
                isCheckable = false
                setTextColor(resources.getColor(R.color.base_blue))
            }
            findViewById<MaterialButton>(R.id.time_2).apply {
                text = "6"
                isCheckable = false
                setTextColor(resources.getColor(R.color.base_blue))
            }
            findViewById<MaterialButton>(R.id.time_3).apply {
                text = "7"
                isCheckable = false
                setTextColor(resources.getColor(R.color.base_blue))
            }
        }
        binding.timeSlotGroup3.timeBtnGroup1.apply {
            findViewById<MaterialButton>(R.id.time_0).apply {
                text = "8"
                isCheckable = false
                setTextColor(resources.getColor(R.color.base_blue))
            }
            findViewById<MaterialButton>(R.id.time_1).apply {
                text = "9"
                isCheckable = false
                setTextColor(resources.getColor(R.color.base_blue))
            }
            findViewById<MaterialButton>(R.id.time_2).apply {
                text = "10"
                isCheckable = false
                setTextColor(resources.getColor(R.color.base_blue))
            }
            findViewById<MaterialButton>(R.id.time_3).apply {
                text = "11"
                isCheckable = false
                setTextColor(resources.getColor(R.color.base_blue))
            }
        }
        binding.timeSlotGroup4.timeBtnGroup1.apply {
            findViewById<MaterialButton>(R.id.time_0).apply {
                text = "12"
                isCheckable = false
                setTextColor(resources.getColor(R.color.base_blue))
            }
            findViewById<MaterialButton>(R.id.time_1).apply {
                text = "13"
                isCheckable = false
                setTextColor(resources.getColor(R.color.base_blue))
            }
            findViewById<MaterialButton>(R.id.time_2).apply {
                text = "14"
                isCheckable = false
                setTextColor(resources.getColor(R.color.base_blue))
            }
            findViewById<MaterialButton>(R.id.time_3).apply {
                text = "15"
                isCheckable = false
                setTextColor(resources.getColor(R.color.base_blue))
            }
        }
        binding.timeSlotGroup5.timeBtnGroup1.apply {
            findViewById<MaterialButton>(R.id.time_0).apply {
                text = "16"
                isCheckable = false
                setTextColor(resources.getColor(R.color.base_blue))
            }
            findViewById<MaterialButton>(R.id.time_1).apply {
                text = "17"
                isCheckable = false
                setTextColor(resources.getColor(R.color.base_blue))
            }
            findViewById<MaterialButton>(R.id.time_2).apply {
                text = "18"
                isCheckable = false
                setTextColor(resources.getColor(R.color.base_blue))
            }
            findViewById<MaterialButton>(R.id.time_3).apply {
                text = "19"
                isCheckable = false
                setTextColor(resources.getColor(R.color.base_blue))
            }
        }
        binding.timeSlotGroup6.timeBtnGroup1.apply {
            findViewById<MaterialButton>(R.id.time_0).apply {
                text = "20"
                isCheckable = false
                setTextColor(resources.getColor(R.color.base_blue))
            }
            findViewById<MaterialButton>(R.id.time_1).apply {
                text = "21"
                isCheckable = false
                setTextColor(resources.getColor(R.color.base_blue))
            }
            findViewById<MaterialButton>(R.id.time_2).apply {
                text = "22"
                isCheckable = false
                setTextColor(resources.getColor(R.color.base_blue))
            }
            findViewById<MaterialButton>(R.id.time_3).apply {
                text = "23"
                isCheckable = false
                setTextColor(resources.getColor(R.color.base_blue))
            }
        }
    }

    private fun setAvailableTime(parkingLot: ParkingLot) {
        for (time in parkingLot.availableTime) {
            when (time.startTime) {
                0 -> {
                    binding.timeSlotGroup1.time0.apply {
                        isCheckable = true
                        setTextColor(resources.getColor(R.color.base_gray))
                    }
                }
                1 -> {
                    binding.timeSlotGroup1.time1.apply {
                        isCheckable = true
                        setTextColor(resources.getColor(R.color.base_gray))
                    }
                }
                2 -> {
                    binding.timeSlotGroup1.time2.apply {
                        isCheckable = true
                        setTextColor(resources.getColor(R.color.base_gray))
                    }
                }
                3 -> {
                    binding.timeSlotGroup1.time3.apply {
                        isCheckable = true
                        setTextColor(resources.getColor(R.color.base_gray))
                    }
                }
                4 -> {
                    binding.timeSlotGroup2.time0.apply {
                        isCheckable = true
                        setTextColor(resources.getColor(R.color.base_gray))
                    }
                }
                5 -> {
                    binding.timeSlotGroup2.time1.apply {
                        isCheckable = true
                        setTextColor(resources.getColor(R.color.base_gray))
                    }
                }
                6 -> {
                    binding.timeSlotGroup2.time2.apply {
                        isCheckable = true
                        setTextColor(resources.getColor(R.color.base_gray))
                    }
                }
                7 -> {
                    binding.timeSlotGroup2.time3.apply {
                        isCheckable = true
                        setTextColor(resources.getColor(R.color.base_gray))
                    }
                }
                8 -> {
                    binding.timeSlotGroup3.time0.apply {
                        isCheckable = true
                        setTextColor(resources.getColor(R.color.base_gray))
                    }
                }
                9 -> {
                    binding.timeSlotGroup3.time1.apply {
                        isCheckable = true
                        setTextColor(resources.getColor(R.color.base_gray))
                    }
                }
                10 -> {
                    binding.timeSlotGroup3.time2.apply {
                        isCheckable = true
                        setTextColor(resources.getColor(R.color.base_gray))
                    }
                }
                11 -> {
                    binding.timeSlotGroup3.time3.apply {
                        isCheckable = true
                        setTextColor(resources.getColor(R.color.base_gray))
                    }
                }
                12 -> {
                    binding.timeSlotGroup4.time0.apply {
                        isCheckable = true
                        setTextColor(resources.getColor(R.color.base_gray))
                    }
                }
                13 -> {
                    binding.timeSlotGroup4.time1.apply {
                        isCheckable = true
                        setTextColor(resources.getColor(R.color.base_gray))
                    }
                }
                14 -> {
                    binding.timeSlotGroup4.time2.apply {
                        isCheckable = true
                        setTextColor(resources.getColor(R.color.base_gray))
                    }
                }
                15 -> {
                    binding.timeSlotGroup4.time3.apply {
                        isCheckable = true
                        setTextColor(resources.getColor(R.color.base_gray))
                    }
                }
                16 -> {
                    binding.timeSlotGroup5.time0.apply {
                        isCheckable = true
                        setTextColor(resources.getColor(R.color.base_gray))
                    }
                }
                17 -> {
                    binding.timeSlotGroup5.time1.apply {
                        isCheckable = true
                        setTextColor(resources.getColor(R.color.base_gray))
                    }
                }
                18 -> {
                    binding.timeSlotGroup5.time2.apply {
                        isCheckable = true
                        setTextColor(resources.getColor(R.color.base_gray))
                    }
                }
                19 -> {
                    binding.timeSlotGroup5.time3.apply {
                        isCheckable = true
                        setTextColor(resources.getColor(R.color.base_gray))
                    }
                }
                20 -> {
                    binding.timeSlotGroup6.time0.apply {
                        isCheckable = true
                        setTextColor(resources.getColor(R.color.base_gray))
                    }
                }
                21 -> {
                    binding.timeSlotGroup6.time1.apply {
                        isCheckable = true
                        setTextColor(resources.getColor(R.color.base_gray))
                    }
                }
                22 -> {
                    binding.timeSlotGroup6.time2.apply {
                        isCheckable = true
                        setTextColor(resources.getColor(R.color.base_gray))
                    }
                }
                23 -> {
                    binding.timeSlotGroup6.time3.apply {
                        isCheckable = true
                        setTextColor(resources.getColor(R.color.base_gray))
                    }
                }
            }
        }
    }

    private fun setUp() {
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

    override fun onResume() {
        super.onResume()
        binding.btnReservationComplete.text = "0원 결제하기"
    }
}