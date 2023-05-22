package org.sopar.presentation.register

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import dagger.hilt.android.AndroidEntryPoint
import org.sopar.R
import org.sopar.databinding.FragmentRegister3Binding
import org.sopar.presentation.base.BaseFragment

@AndroidEntryPoint
class RegisterFragment3 : BaseFragment<FragmentRegister3Binding>(R.layout.fragment_register3) {
    private val checkedList = mutableSetOf<String>()
    private val viewModel by activityViewModels<RegisterViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUp()
    }

    private fun setUp() {
        binding.apply {
            typeLight.textType.text = "경차"
            typeSmall.textType.text = "소형"
            typeMid.textType.text = "중형"
            typeLarge.textType.text = "대형"
            typeRv.textType.text = "RV"
            typeEv.textType.text = "EV"
            typeCargo.textType.text = "화물"
        }

        binding.typeLight.typeCheckBox.setOnCheckedChangeListener { _, b ->
            if (b) {
                checkedList.add("경차")
            } else {
                checkedList.remove("경차")
            }
        }
        binding.typeSmall.typeCheckBox.setOnCheckedChangeListener { _, b ->
            if (b) {
                checkedList.add("소형")
            } else {
                checkedList.remove("소형")
            }
        }
        binding.typeMid.typeCheckBox.setOnCheckedChangeListener { _, b ->
            if (b) {
                checkedList.add("중형")
            } else {
                checkedList.remove("중형")
            }
        }
        binding.typeLarge.typeCheckBox.setOnCheckedChangeListener { _, b ->
            if (b) {
                checkedList.add("대형")
            } else {
                checkedList.remove("대형")
            }
        }
        binding.typeRv.typeCheckBox.setOnCheckedChangeListener { _, b ->
            if (b) {
                checkedList.add("rv")
            } else {
                checkedList.remove("rv")
            }
        }
        binding.typeEv.typeCheckBox.setOnCheckedChangeListener { _, b ->
            if (b) {
                checkedList.add("ev")
            } else {
                checkedList.remove("ev")
            }
        }
        binding.typeCargo.typeCheckBox.setOnCheckedChangeListener { _, b ->
            if (b) {
                checkedList.add("화물")
            } else {
                checkedList.remove("화물")
            }
        }

        binding.btnNextStep.setOnClickListener {
            Log.d("checked list", checkedList.toString())
            if (checkedList.size > 0) {
                viewModel.type = checkedList.toList()

                val viewPager = requireActivity().findViewById<ViewPager2>(R.id.register_view_pager)
                viewPager.setCurrentItem(3, true)
            } else {
                Toast.makeText(context, "주차 가능한 차종을 모두 알려주세요!", Toast.LENGTH_SHORT).show()
            }

        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRegister3Binding {
        return FragmentRegister3Binding.inflate(inflater, container, false)
    }

}