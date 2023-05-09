package org.sopar.presentation.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.sopar.R
import org.sopar.databinding.FragmentRegister1Binding
import org.sopar.presentation.base.BaseFragment

class RegisterFragment1 : BaseFragment<FragmentRegister1Binding>(R.layout.fragment_register1) {

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRegister1Binding {
        return FragmentRegister1Binding.inflate(inflater, container, false)
    }

}