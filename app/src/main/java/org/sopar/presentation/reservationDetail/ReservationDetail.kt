package org.sopar.presentation.reservationDetail

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import org.sopar.R
import org.sopar.databinding.FragmentReservationDetailBinding
import org.sopar.domain.entity.NetworkState
import org.sopar.presentation.base.BaseErrorDialog
import org.sopar.presentation.base.BaseFragment
import org.sopar.presentation.main.MainVIewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class ReservationDetail : BaseFragment<FragmentReservationDetailBinding>(R.layout.fragment_reservation_detail) {
    private val reservationDetailViewModel by viewModels<ReservationDetailViewModel> ()
    private val args by navArgs<ReservationDetailArgs>()
    private val mainViewModel by activityViewModels<MainVIewModel>()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentReservationDetailBinding {
        return FragmentReservationDetailBinding.inflate(inflater, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObserve()
        init()
        setBtnDeleteListener()
    }

    private fun setBtnDeleteListener() {
        binding.btnReservationCancel.setOnClickListener {
            val dialog = ReservationDeleteDialog(reservationDetailViewModel, args.reservationId)
            dialog.show(requireActivity().supportFragmentManager, "ReservationDeleteDialog")
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setObserve() {
        reservationDetailViewModel.reservation.observe(viewLifecycleOwner) { reservation ->
            binding.textParkingLotName.text = args.parkingLotName
            binding.textReservationPrice.text = reservation.price.toString()

            reservation.monthlyReservation?.let {
                val date = LocalDateTime.parse(it.date, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"))
                binding.textReservationDate.text = date.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"))
                binding.textMonthDuration .text = it.duration.toString()
                binding.monthlyReservationInfo.visibility = View.VISIBLE
            }

            reservation.hourlyReservation?.let {
                val date = LocalDateTime.parse(it.date, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"))
                binding.textReservationDate.text = date.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"))
                val times = it.duration.sorted()
                if (times.size > 1){
                    binding.textTimeDuration.text = "${times[0]} - ${times[times.size - 1]}"
                } else {
                    binding.textTimeDuration.text = times.toString()
                }

                binding.hourlyReservationInfo.visibility = View.VISIBLE
            }

        }

        reservationDetailViewModel.deleteReservationState.observe(viewLifecycleOwner) { state ->
            if (state == NetworkState.FAIL) {
                val dialog = BaseErrorDialog(R.string.delete_reservation_error)
                dialog.show(requireActivity().supportFragmentManager, "DeleteReservationErrorDialog")
            } else if (state == NetworkState.SUCCESS) {
                mainViewModel.getUserInfoById()
                Toast.makeText(context, "성공적으로 삭제되었습니다!🙌", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_reservationDetail_to_fragment_my_reservation)
            }
        }
    }

    private fun init() {
        reservationDetailViewModel.getReservationById(args.reservationId)
    }
}