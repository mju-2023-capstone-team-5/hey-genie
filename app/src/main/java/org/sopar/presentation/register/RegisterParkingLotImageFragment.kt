package org.sopar.presentation.register

import android.view.LayoutInflater
import android.view.ViewGroup
import org.sopar.R
import org.sopar.databinding.FragmentRegisterParkingLotImageBinding
import org.sopar.presentation.base.BaseFragment

class RegisterParkingLotImageFragment: BaseFragment<FragmentRegisterParkingLotImageBinding>(R.layout.fragment_register_parking_lot_image) {

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRegisterParkingLotImageBinding {
        return FragmentRegisterParkingLotImageBinding.inflate(inflater, container, false)
    }

}