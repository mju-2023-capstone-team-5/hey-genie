package org.sopar.presentation.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import dagger.hilt.android.AndroidEntryPoint
import org.sopar.R
import org.sopar.databinding.FragmentRegister2Binding
import org.sopar.presentation.base.BaseFragment

@AndroidEntryPoint
class RegisterFragment2 : BaseFragment<FragmentRegister2Binding>(R.layout.fragment_register2) {
    private val viewModel by activityViewModels<RegisterViewModel>()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRegister2Binding {
        return FragmentRegister2Binding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnNextStep.setOnClickListener {
            val name = binding.edtTextName.text.toString()
            val totalSpace = binding.edtTextSpace.text.toString()
            if (name.isNotEmpty() && totalSpace.isNotEmpty()) {
                if (totalSpace.toIntOrNull() == null) {
                    Toast.makeText(context, "공유 면수를 숫자 형식으로 입력해주세요!", Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.name = binding.edtTextName.text.toString()
                    viewModel.totalSpace = Integer.parseInt(binding.edtTextSpace.text.toString())

                    val viewPager = requireActivity().findViewById<ViewPager2>(R.id.register_view_pager)
                    viewPager.setCurrentItem(2, true)
                }
            } else {
                Toast.makeText(context, "필요한 정보를 모두 입력해주세요!", Toast.LENGTH_SHORT).show()
            }

        }
    }

}