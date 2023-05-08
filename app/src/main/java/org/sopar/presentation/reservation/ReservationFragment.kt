package org.sopar.presentation.reservation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import org.sopar.R
import org.sopar.databinding.FragmentReservationBinding
import org.sopar.presentation.base.BaseFragment

class ReservationFragment : BaseFragment<FragmentReservationBinding>(R.layout.fragment_reservation) {
    private val args: ReservationFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUp()
        setNumberPicker()
        setCompleteBtnListener()

    }

    private fun setCompleteBtnListener() {
        binding.btnReservationComplete.setOnClickListener {
            
        }
    }

    private fun setNumberPicker() {
        binding.timeDurationPicker.apply {
            minValue = 1
            maxValue = 12
            value = 1
        }

        binding.monthlyDurationPicker.apply {
            minValue = 1
            maxValue = 12
            value = 1
        }
    }

    private fun setUp() {
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