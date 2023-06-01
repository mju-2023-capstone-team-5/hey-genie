package org.sopar.presentation.myReservation

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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

    @RequiresApi(Build.VERSION_CODES.O)
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

        myReservationViewModel.registerGrade.observe(viewLifecycleOwner) { state ->
            if(state == NetworkState.SUCCESS) {
                Toast.makeText(context, "소중한 후기가 등록되었습니다! 감사합니다!🙌", Toast.LENGTH_SHORT).show()
            } else if (state == NetworkState.FAIL) {
                Toast.makeText(context, "후기 등록에 실패했습니다!\n다시 시도해주세요!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun init() {
        myReservationViewModel.getReservationByUser()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setRecyclerViewAdapter() {
        reservationAdapter.setOnItemClickListener { reservation ->
            val action = MyReservationDirections.actionFragmentMyReservationToReservationDetail(reservationId = reservation.reservationId, parkingLotName = reservation.parkingLotName)
            findNavController().navigate(action)
        }

        reservationAdapter.setOnCommentBtnClickListener { reservation ->
            val endTime = LocalDateTime.parse(reservation.endTime, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"))
            Log.d("endTime", endTime.toString())
            if (endTime < LocalDateTime.now()) {
                val dialog = CommentDialog(myReservationViewModel, reservation.reservationId)
                dialog.show(requireActivity().supportFragmentManager, "RegisterCommentDialog")
            } else {
                Toast.makeText(context, "사용한 주차장에 대해서만 후기를 작성할 수 있습니다!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.listReservation.apply {
            setHasFixedSize(true)
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = reservationAdapter
        }
    }

}