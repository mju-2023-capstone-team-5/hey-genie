package org.sopar.presentation.register

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import dagger.hilt.android.AndroidEntryPoint
import org.sopar.R
import org.sopar.databinding.FragmentRegister1Binding
import org.sopar.presentation.base.BaseFragment
import org.sopar.presentation.base.LocationSearchAdapter

@AndroidEntryPoint
class RegisterFragment1 : BaseFragment<FragmentRegister1Binding>(R.layout.fragment_register1) {
    private val viewModel by activityViewModels<RegisterViewModel>()
    private lateinit var locationSearchAdapter: LocationSearchAdapter

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRegister1Binding {
        return FragmentRegister1Binding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setObserver()
        setupEditTextListener()
        init()
    }

    private fun init() {
        binding.btnNextStep.setOnClickListener {
            if ((binding.edtTextAddress.text.toString().isNotEmpty()) and (viewModel.latitude != null)) {
                val viewPager = requireActivity().findViewById<ViewPager2>(R.id.register_view_pager)
                viewPager.setCurrentItem(1, true)
            } else {
                Toast.makeText(context, "주차장 주소를 알려주세요!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setObserver() {
        viewModel.searchResult.observe(viewLifecycleOwner) { result ->
            if (result.isNotEmpty()) {
                locationSearchAdapter.submitList(result)
            }
        }
    }

    private fun setupRecyclerView() {
        locationSearchAdapter = LocationSearchAdapter()

        locationSearchAdapter.setOnItemClickListener { place ->
            binding.edtTextAddress.setText(place.road_address_name)
            viewModel.latitude = place.x.toDouble()
            viewModel.longitude = place.y.toDouble()
            viewModel.address = place.road_address_name
        }

        binding.listSearchResult.apply {
            //정해진 사이즈가 있으니 새로운 요소를 추가할 때 recyclerview의 크기를 재측정 하지 않아도 된다.
            setHasFixedSize(true)
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = locationSearchAdapter
        }

    }

    private fun setupEditTextListener() {
        binding.edtTextAddress.requestFocus()
        binding.edtTextAddress.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //입력하기 전
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                p0?.let {
                    if (p0.isNotEmpty()) {
                        viewModel.getSearchLocation(p0.toString())
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                //입력이 끝났을 때
            }

        })
    }
}