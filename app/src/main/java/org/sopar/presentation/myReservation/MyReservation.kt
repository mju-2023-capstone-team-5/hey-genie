package org.sopar.presentation.myReservation

import android.view.LayoutInflater
import android.view.ViewGroup
import org.sopar.R
import org.sopar.databinding.FragmentMyReservationBinding
import org.sopar.presentation.base.BaseFragment

class MyReservation : BaseFragment<FragmentMyReservationBinding>(R.layout.fragment_my_reservation) {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMyReservationBinding {
        return FragmentMyReservationBinding.inflate(inflater, container, false)
    }
}