package org.sopar.presentation.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.sopar.R
import org.sopar.databinding.FragmentRegister3Binding
import org.sopar.presentation.base.BaseFragment

class RegisterFragment3 : BaseFragment<FragmentRegister3Binding>(R.layout.fragment_register3) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUp()
    }

    private fun setUp() {
        binding.apply {
            typeLight.textType.text = "경차"
            typeSmall.textType.text = "소형"
            typeMid.textType.text = "중형"
            typeLarge.textType.text = "대형"
            typeRv.textType.text = "RV"
            typeEv.textType.text = "EV"
            typeCargo.textType.text = "화물"
        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRegister3Binding {
        return FragmentRegister3Binding.inflate(inflater, container, false)
    }

}