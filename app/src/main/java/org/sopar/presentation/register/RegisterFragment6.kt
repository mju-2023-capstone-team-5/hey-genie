package org.sopar.presentation.register

import android.view.LayoutInflater
import android.view.ViewGroup
import org.sopar.R
import org.sopar.databinding.FragmentRegister6Binding
import org.sopar.presentation.base.BaseFragment

class RegisterFragment6 : BaseFragment<FragmentRegister6Binding>(R.layout.fragment_register6) {

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRegister6Binding {
        return FragmentRegister6Binding.inflate(inflater, container, false)
    }

}