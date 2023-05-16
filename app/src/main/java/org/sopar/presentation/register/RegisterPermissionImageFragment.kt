package org.sopar.presentation.register

import android.view.LayoutInflater
import android.view.ViewGroup
import org.sopar.R
import org.sopar.databinding.FragmentRegisterPermissionImageBinding
import org.sopar.presentation.base.BaseFragment

class RegisterPermissionImageFragment: BaseFragment<FragmentRegisterPermissionImageBinding>(R.layout.fragment_register_permission_image) {

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRegisterPermissionImageBinding {
        return FragmentRegisterPermissionImageBinding.inflate(inflater, container, false)
    }

}