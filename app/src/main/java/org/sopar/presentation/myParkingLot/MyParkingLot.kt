package org.sopar.presentation.myParkingLot

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.sopar.R
import org.sopar.databinding.FragmentMyParkingLotBinding
import org.sopar.presentation.base.BaseFragment

class MyParkingLot : BaseFragment<FragmentMyParkingLotBinding>(R.layout.fragment_my_parking_lot) {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMyParkingLotBinding {
        return FragmentMyParkingLotBinding.inflate(inflater, container, false)
    }
}