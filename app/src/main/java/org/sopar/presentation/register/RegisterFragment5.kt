package org.sopar.presentation.register

import android.view.LayoutInflater
import android.view.ViewGroup
import org.sopar.R
import org.sopar.databinding.FragmentRegister5Binding
import org.sopar.presentation.base.BaseFragment

class RegisterFragment5 : BaseFragment<FragmentRegister5Binding>(R.layout.fragment_register5){

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRegister5Binding {
        return FragmentRegister5Binding.inflate(inflater, container, false)
    }

}