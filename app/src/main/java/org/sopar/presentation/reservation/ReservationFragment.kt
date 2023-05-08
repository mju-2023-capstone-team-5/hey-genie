package org.sopar.presentation.reservation

import android.view.LayoutInflater
import android.view.ViewGroup
import org.sopar.R
import org.sopar.databinding.FragmentReservationBinding
import org.sopar.presentation.base.BaseFragment

class ReservationFragment : BaseFragment<FragmentReservationBinding>(R.layout.fragment_reservation) {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentReservationBinding {
        return FragmentReservationBinding.inflate(inflater, container, false)
    }
}