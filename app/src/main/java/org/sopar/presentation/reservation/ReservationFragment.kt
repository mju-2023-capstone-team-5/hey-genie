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
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentReservationBinding {
        return FragmentReservationBinding.inflate(inflater, container, false)
    }
}