package org.sopar.presentation.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.sopar.R
import org.sopar.databinding.FragmentRegister2Binding
import org.sopar.presentation.base.BaseFragment

class RegisterFragment2 : BaseFragment<FragmentRegister2Binding>(R.layout.fragment_register2) {

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRegister2Binding {
        return FragmentRegister2Binding.inflate(inflater, container, false)
    }

}