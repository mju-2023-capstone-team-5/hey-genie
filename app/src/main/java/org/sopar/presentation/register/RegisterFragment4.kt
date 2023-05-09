package org.sopar.presentation.register

import android.view.LayoutInflater
import android.view.ViewGroup
import org.sopar.R
import org.sopar.databinding.FragmentRegister4Binding
import org.sopar.presentation.base.BaseFragment

class RegisterFragment4 : BaseFragment<FragmentRegister4Binding>(R.layout.fragment_register4) {

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRegister4Binding {
        return FragmentRegister4Binding.inflate(inflater, container, false)
    }

}