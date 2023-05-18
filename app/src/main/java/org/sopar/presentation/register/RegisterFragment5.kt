package org.sopar.presentation.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import dagger.hilt.android.AndroidEntryPoint
import org.sopar.R
import org.sopar.data.remote.response.Rate
import org.sopar.databinding.FragmentRegister5Binding
import org.sopar.presentation.base.BaseFragment

@AndroidEntryPoint
class RegisterFragment5 : BaseFragment<FragmentRegister5Binding>(R.layout.fragment_register5){
    private val viewModel by viewModels<RegisterViewModel>()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRegister5Binding {
        return FragmentRegister5Binding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToggleListener()
        setNextBtnListener()
    }

    private fun setToggleListener() {
        binding.switchForHourlyParking.setOnCheckedChangeListener { _ , b ->
            if (b) {
                binding.layoutHourlyParking.visibility = View.VISIBLE
            } else {
                binding.layoutHourlyParking.visibility = View.GONE
            }
        }

        binding.switchForMonthlyParking.setOnCheckedChangeListener { _, b ->
            if (b) {
                binding.layoutMonthlyParking.visibility = View.VISIBLE
            } else {
                binding.layoutMonthlyParking.visibility = View.GONE
            }
        }
    }

    private fun setNextBtnListener() {
        binding.btnNextStep.setOnClickListener {
            val hourlyMin = binding.edtTextHourlyMinCost.text.toString()
            val hourlySurcharge = binding.edtTextHourlySurcharge.text.toString()
            val monthlyMin = binding.edtTextMonthlyMinCost.text.toString()
            val monthlySurcharge = binding.edtTextMonthlySurcharge.text.toString()

            if (binding.switchForHourlyParking.isChecked or binding.switchForMonthlyParking.isChecked) {
                if (checkHourly(hourlyMin, hourlySurcharge) and checkMonthly(monthlyMin, monthlySurcharge)) {
                    if (binding.switchForHourlyParking.isChecked) {
                        viewModel.hourly = Rate(hourlyMin.toInt(), hourlySurcharge.toInt())
                    }
                    if (binding.switchForMonthlyParking.isChecked) {
                        viewModel.hourly = Rate(monthlyMin.toInt(), monthlySurcharge.toInt())
                    }
                    val viewPager = requireActivity().findViewById<ViewPager2>(R.id.register_view_pager)
                    viewPager.setCurrentItem(5, true)
                } else {
                    Toast.makeText(context, "숫자로 필요한 값을 모두 채워주세요!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "시간/월별 주차 중\n최소 하나는 선택해주세요!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkHourly(hourlyMin: String, hourlySurcharge: String): Boolean {
        return if (!binding.switchForHourlyParking.isChecked){
            true
        } else{
            (hourlyMin.toIntOrNull() != null) and (hourlySurcharge.toIntOrNull() != null)
        }
    }

    private fun checkMonthly(monthlyMin: String, monthlySurcharge: String): Boolean{
        return if (!binding.switchForMonthlyParking.isChecked){
            true
        } else {
            (monthlyMin.toIntOrNull() != null) and (monthlySurcharge.toIntOrNull() != null)
        }
    }

}