package org.sopar.presentation.pay

import android.view.LayoutInflater
import android.view.ViewGroup
import org.sopar.R
import org.sopar.databinding.FragmentPayBinding
import org.sopar.presentation.base.BaseFragment

class PayFragment : BaseFragment<FragmentPayBinding>(R.layout.fragment_pay) {

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPayBinding {
        return FragmentPayBinding.inflate(inflater, container, false)
    }

}