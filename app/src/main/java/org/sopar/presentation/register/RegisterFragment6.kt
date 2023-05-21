package org.sopar.presentation.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import dagger.hilt.android.AndroidEntryPoint
import org.sopar.R
import org.sopar.databinding.FragmentRegister6Binding
import org.sopar.presentation.base.BaseFragment

@AndroidEntryPoint
class RegisterFragment6 : BaseFragment<FragmentRegister6Binding>(R.layout.fragment_register6) {
    private val viewModel by viewModels<RegisterViewModel>()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRegister6Binding {
        return FragmentRegister6Binding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setNextBtnListener()
    }

    private fun setNextBtnListener() {
        binding.btnNextStep.setOnClickListener {
            val phoneNumber = binding.edtTextPhoneNumber.text.toString()
            val caution = binding.edtTextCaution.text.toString()

            if (phoneNumber.isNotEmpty()) {
                viewModel.phoneNumber = phoneNumber
                viewModel.freeInformation = caution

                val viewPager = requireActivity().findViewById<ViewPager2>(R.id.register_view_pager)
                viewPager.setCurrentItem(6, true)
            } else {
                Toast.makeText(context, "필요한 값을 모두 채워주세요!", Toast.LENGTH_SHORT).show()
            }
        }
    }

}