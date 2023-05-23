package org.sopar.presentation.register

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint
import org.sopar.R
import org.sopar.data.remote.response.TimeSlot
import org.sopar.databinding.FragmentRegister4Binding
import org.sopar.presentation.base.BaseFragment

@AndroidEntryPoint
class RegisterFragment4 : BaseFragment<FragmentRegister4Binding>(R.layout.fragment_register4) {
    private val viewModel by activityViewModels<RegisterViewModel>()
    private val checkedDay = mutableSetOf<String>()
    private val checkedTime = mutableSetOf<Int>()
    private val timeSlots = mutableListOf<TimeSlot>()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRegister4Binding {
        return FragmentRegister4Binding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTime()
        setDayListener()
        setTimeListener()
        init()

    }

    private fun init() {
        binding.btnNextStep.setOnClickListener {
            Log.e("checked_list", checkedDay.toString())
            if ((checkedDay.size > 0) and (checkedTime.size > 0)) {
                viewModel.availableDay = checkedDay.toList()

                getTime()
                viewModel.availableTime = timeSlots

                val viewPager = requireActivity().findViewById<ViewPager2>(R.id.register_view_pager)
                viewPager.setCurrentItem(4, true)

            } else {
                Toast.makeText(context, "필요한 정보를 모두 입력해주세요!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getTime() {
        for (time in checkedTime) {
            val temp = TimeSlot(time, 0, time+1, 0)
            timeSlots.add(temp)
        }
    }

    private fun setTime() {
        binding.timeSlotGroup1.timeBtnGroup1.apply {
            findViewById<MaterialButton>(R.id.time_0).text = "0"
            findViewById<MaterialButton>(R.id.time_1).text = "1"
            findViewById<MaterialButton>(R.id.time_2).text = "2"
            findViewById<MaterialButton>(R.id.time_3).text = "3"
        }
        binding.timeSlotGroup2.timeBtnGroup1.apply {
            findViewById<MaterialButton>(R.id.time_0).text = "4"
            findViewById<MaterialButton>(R.id.time_1).text = "5"
            findViewById<MaterialButton>(R.id.time_2).text = "6"
            findViewById<MaterialButton>(R.id.time_3).text = "7"
        }
        binding.timeSlotGroup3.timeBtnGroup1.apply {
            findViewById<MaterialButton>(R.id.time_0).text = "8"
            findViewById<MaterialButton>(R.id.time_1).text = "9"
            findViewById<MaterialButton>(R.id.time_2).text = "10"
            findViewById<MaterialButton>(R.id.time_3).text = "11"
        }
        binding.timeSlotGroup4.timeBtnGroup1.apply {
            findViewById<MaterialButton>(R.id.time_0).text = "12"
            findViewById<MaterialButton>(R.id.time_1).text = "13"
            findViewById<MaterialButton>(R.id.time_2).text = "14"
            findViewById<MaterialButton>(R.id.time_3).text = "15"
        }
        binding.timeSlotGroup5.timeBtnGroup1.apply {
            findViewById<MaterialButton>(R.id.time_0).text = "16"
            findViewById<MaterialButton>(R.id.time_1).text = "17"
            findViewById<MaterialButton>(R.id.time_2).text = "18"
            findViewById<MaterialButton>(R.id.time_3).text = "19"
        }
        binding.timeSlotGroup6.timeBtnGroup1.apply {
            findViewById<MaterialButton>(R.id.time_0).text = "20"
            findViewById<MaterialButton>(R.id.time_1).text = "21"
            findViewById<MaterialButton>(R.id.time_2).text = "22"
            findViewById<MaterialButton>(R.id.time_3).text = "23"
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
                }
                R.id.time_1 -> {
                    if (isChecked) {
                        checkedTime.add(1)
                    } else {
                        checkedTime.remove(1)
                    }
                }
                R.id.time_2 -> {
                    if (isChecked) {
                        checkedTime.add(2)
                    } else {
                        checkedTime.remove(2)
                    }
                }
                R.id.time_3 -> {
                    if (isChecked) {
                        checkedTime.add(3)
                    } else {
                        checkedTime.remove(3)
                    }
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
                }
                R.id.time_1 -> {
                    if (isChecked) {
                        checkedTime.add(5)
                    } else {
                        checkedTime.remove(5)
                    }
                }
                R.id.time_2 -> {
                    if (isChecked) {
                        checkedTime.add(6)
                    } else {
                        checkedTime.remove(6)
                    }
                }
                R.id.time_3 -> {
                    if (isChecked) {
                        checkedTime.add(7)
                    } else {
                        checkedTime.remove(7)
                    }
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
                }
                R.id.time_1 -> {
                    if (isChecked) {
                        checkedTime.add(9)
                    } else {
                        checkedTime.remove(9)
                    }
                }
                R.id.time_2 -> {
                    if (isChecked) {
                        checkedTime.add(10)
                    } else {
                        checkedTime.remove(10)
                    }
                }
                R.id.time_3 -> {
                    if (isChecked) {
                        checkedTime.add(11)
                    } else {
                        checkedTime.remove(11)
                    }
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
                }
                R.id.time_1 -> {
                    if (isChecked) {
                        checkedTime.add(13)
                    } else {
                        checkedTime.remove(13)
                    }
                }
                R.id.time_2 -> {
                    if (isChecked) {
                        checkedTime.add(14)
                    } else {
                        checkedTime.remove(14)
                    }
                }
                R.id.time_3 -> {
                    if (isChecked) {
                        checkedTime.add(15)
                    } else {
                        checkedTime.remove(15)
                    }
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
                }
                R.id.time_1 -> {
                    if (isChecked) {
                        checkedTime.add(17)
                    } else {
                        checkedTime.remove(17)
                    }
                }
                R.id.time_2 -> {
                    if (isChecked) {
                        checkedTime.add(18)
                    } else {
                        checkedTime.remove(18)
                    }
                }
                R.id.time_3 -> {
                    if (isChecked) {
                        checkedTime.add(19)
                    } else {
                        checkedTime.remove(19)
                    }
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
                }
                R.id.time_1 -> {
                    if (isChecked) {
                        checkedTime.add(21)
                    } else {
                        checkedTime.remove(21)
                    }
                }
                R.id.time_2 -> {
                    if (isChecked) {
                        checkedTime.add(22)
                    } else {
                        checkedTime.remove(22)
                    }
                }
                R.id.time_3 -> {
                    if (isChecked) {
                        checkedTime.add(23)
                    } else {
                        checkedTime.remove(23)
                    }
                }

            }
        }
    }

    private fun setDayListener() {
        binding.dayBtnGroup1.addOnButtonCheckedListener { _, checkedId, isChecked ->
            when(checkedId) {
                R.id.day_of_month_mon -> {
                    if (isChecked) {
                        checkedDay.add("월")
                    } else {
                        checkedDay.remove("월")
                    }
                }
                R.id.day_of_month_tue -> {
                    if (isChecked) {
                        checkedDay.add("화")
                    } else {
                        checkedDay.remove("화")
                    }
                }
                R.id.day_of_month_wed -> {
                    if (isChecked) {
                        checkedDay.add("수")
                    } else {
                        checkedDay.remove("수")
                    }
                }
                R.id.day_of_month_thu -> {
                    if (isChecked) {
                        checkedDay.add("목")
                    } else {
                        checkedDay.remove("목")
                    }
                }
            }
        }
        binding.dayBtnGroup2.addOnButtonCheckedListener { _, checkedId, isChecked ->
            when(checkedId) {
                R.id.day_of_month_fri -> {
                    if (isChecked) {
                        checkedDay.add("금")
                    } else {
                        checkedDay.remove("금")
                    }
                }
                R.id.day_of_month_sau -> {
                    if (isChecked) {
                        checkedDay.add("토")
                    } else {
                        checkedDay.remove("토")
                    }
                }
                R.id.day_of_month_sun -> {
                    if (isChecked) {
                        checkedDay.add("일")
                    } else {
                        checkedDay.remove("일")
                    }
                }
            }
        }
    }
}