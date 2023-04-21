package org.sopar.presentation.map

import android.view.LayoutInflater
import android.view.ViewGroup
import org.sopar.R
import org.sopar.databinding.FragmentMapBinding
import org.sopar.presentation.base.BaseFragment

class MapFragment : BaseFragment<FragmentMapBinding>(R.layout.fragment_map) {

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMapBinding {
        return FragmentMapBinding.inflate(inflater, container, false)
    }

}