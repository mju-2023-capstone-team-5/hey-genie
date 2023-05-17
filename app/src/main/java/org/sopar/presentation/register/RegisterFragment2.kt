package org.sopar.presentation.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import dagger.hilt.android.AndroidEntryPoint
import org.sopar.R
import org.sopar.databinding.FragmentRegister2Binding
import org.sopar.presentation.base.BaseFragment

@AndroidEntryPoint
class RegisterFragment2 : BaseFragment<FragmentRegister2Binding>(R.layout.fragment_register2) {
    private val viewModel by viewModels<RegisterViewModel>()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRegister2Binding {
        return FragmentRegister2Binding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnNextStep.setOnClickListener {
            viewModel.name = binding.edtTextName.text.toString()
            viewModel.totalSpace = Integer.parseInt(binding.edtTextSpace.text.toString())

            val viewPager = requireActivity().findViewById<ViewPager2>(R.id.register_view_pager)
            viewPager.setCurrentItem(2, true)
        }
    }

}