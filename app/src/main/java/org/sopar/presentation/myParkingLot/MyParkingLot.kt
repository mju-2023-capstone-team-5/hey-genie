package org.sopar.presentation.myParkingLot

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import org.sopar.R
import org.sopar.databinding.FragmentMyParkingLotBinding
import org.sopar.domain.entity.NetworkState
import org.sopar.presentation.base.BaseErrorDialog
import org.sopar.presentation.base.BaseFragment

@AndroidEntryPoint
class MyParkingLot : BaseFragment<FragmentMyParkingLotBinding>(R.layout.fragment_my_parking_lot) {
    private val myParkingLotViewModel by viewModels<MyParkingLotViewModel>()
    private val parkingLotAdapter: MyParkingLotAdapter = MyParkingLotAdapter()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMyParkingLotBinding {
        return FragmentMyParkingLotBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerViewAdapter()
        setObserve()
        myParkingLotViewModel.getParkingLotByUserId()
    }

    private fun setObserve() {
        myParkingLotViewModel.parkingLots.observe(viewLifecycleOwner) { parkingLotList ->
            if (parkingLotList?.isNotEmpty() == true) {
                Log.d("parkingLotObserve", parkingLotList.toString())
                binding.listParkingLot.visibility = View.VISIBLE
                binding.textNoRegisteredParkingLot.visibility = View.GONE
                parkingLotAdapter.submitList(parkingLotList.toMutableList())
            } else {
                binding.listParkingLot.visibility = View.GONE
                binding.textNoRegisteredParkingLot.visibility = View.VISIBLE
            }
        }

        myParkingLotViewModel.getParkingLotStatus.observe(viewLifecycleOwner) { status ->
            if (status == NetworkState.FAIL) {
                val dialog = BaseErrorDialog(R.string.get_parking_lot_error)
                dialog.show(requireActivity().supportFragmentManager, "getParkingLotErrorDialog")
            }
        }
    }

    private fun setRecyclerViewAdapter() {
        parkingLotAdapter.setOnItemClickListener { parkingLot ->
            val action = MyParkingLotDirections.actionFragmentMyParkingLotToParkingLotDetailFragment(parkingLot)
            findNavController().navigate(action)
        }

        binding.listParkingLot.apply {
            setHasFixedSize(true)
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = parkingLotAdapter
        }
    }

}