package org.sopar.presentation.myReservation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import org.sopar.R
import org.sopar.databinding.FragmentMyReservationBinding
import org.sopar.domain.entity.NetworkState
import org.sopar.presentation.base.BaseErrorDialog
import org.sopar.presentation.base.BaseFragment
import org.sopar.presentation.myParkingLot.MyParkingLotDirections

@AndroidEntryPoint
class MyReservation : BaseFragment<FragmentMyReservationBinding>(R.layout.fragment_my_reservation) {
    private val reservationAdapter = MyReservationAdapter()
    private val myReservationViewModel by viewModels<MyReservationViewModel>()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMyReservationBinding {
        return FragmentMyReservationBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerViewAdapter()
        setObserve()
        init()
    }

    private fun setObserve() {
        myReservationViewModel.reservations.observe(viewLifecycleOwner) { reservations ->
            reservations?.let {
                if (reservations.isNotEmpty()) {
                    binding.listReservation.visibility = View.VISIBLE
                    binding.textNoReservation.visibility = View.GONE
                    reservationAdapter.submitList(reservations.toMutableList())
                } else {
                    binding.listReservation.visibility = View.GONE
                    binding.textNoReservation.visibility = View.VISIBLE
                }
            }
        }

        myReservationViewModel.getReservationState.observe(viewLifecycleOwner) { state ->
            if (state == NetworkState.FAIL) {
                val dialog = BaseErrorDialog(R.string.get_reservation_error)
                dialog.show(requireActivity().supportFragmentManager, "GetReservationErrorDialog")
            }
        }
    }

    private fun init() {
        myReservationViewModel.getReservationByUser()
    }

    private fun setRecyclerViewAdapter() {
        reservationAdapter.setOnItemClickListener { reservation ->
            val action = MyReservationDirections.actionFragmentMyReservationToReservationDetail(reservationId = reservation.reservationId, parkingLotName = reservation.parkingLotName)
            findNavController().navigate(action)
        }

        binding.listReservation.apply {
            setHasFixedSize(true)
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = reservationAdapter
        }
    }

}