package org.sopar.presentation.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import org.sopar.R
import org.sopar.databinding.FragmentSearchBinding
import org.sopar.domain.entity.NetworkState
import org.sopar.presentation.base.BaseErrorDialog
import org.sopar.presentation.base.BaseFragment
import org.sopar.presentation.base.LocationSearchAdapter

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {
    private lateinit var locationSearchAdapter: LocationSearchAdapter
    private val searchViewModel by viewModels<SearchViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setObserver()
        setupEditTextListener()
        setupBackStack()
    }

    private fun setObserver() {
        searchViewModel.searchState.observe(viewLifecycleOwner) { state ->
            if (state == NetworkState.FAIL) {
                Log.d("SearchFragment", "searchState Error")
                val dialog = BaseErrorDialog(R.string.base_error)
                dialog.show(requireActivity().supportFragmentManager, "BaseErrorDialog")
            }
        }

        searchViewModel.searchResult.observe(viewLifecycleOwner) { result ->
            if (result.isNotEmpty()) {
                locationSearchAdapter.submitList(result)
            }
        }
    }

    private fun setupBackStack() {
        binding.imageBack.setOnClickListener {
            findNavController().navigate(R.id.action_searchFragment_to_fragment_map)
        }
    }

    private fun setupRecyclerView() {
        locationSearchAdapter = LocationSearchAdapter()

        locationSearchAdapter.setOnItemClickListener { place ->
            val action = SearchFragmentDirections.actionSearchFragmentToFragmentMap(place)
            findNavController().navigate(action)
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
        binding.textMapSearch.requestFocus()
        binding.textMapSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //입력하기 전
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
               p0?.let {
                  if (p0.isNotEmpty()) {
                      searchViewModel.getSearchLocation(p0.toString())
                  }
               }
            }

            override fun afterTextChanged(p0: Editable?) {
                //입력이 끝났을 때
            }

        })
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSearchBinding {
        return FragmentSearchBinding.inflate(inflater, container, false)
    }

}