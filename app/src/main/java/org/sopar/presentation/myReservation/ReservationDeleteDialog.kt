package org.sopar.presentation.myReservation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.sopar.R
import org.sopar.data.remote.response.Notice
import org.sopar.databinding.DialogNoticeBinding
import org.sopar.databinding.DialogReservationDeleteBinding
import org.sopar.presentation.base.BaseDialog

class ReservationDeleteDialog(private val myReservationViewModel: MyReservationViewModel, private val reservationId: Int): BaseDialog<DialogReservationDeleteBinding>(R.layout.dialog_reservation_delete){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        binding.btnDelete.setOnClickListener {
            myReservationViewModel.deleteReservationById(reservationId)
        }

        return binding.root
    }

    override fun getViewBinding(): DialogReservationDeleteBinding {
        return DialogReservationDeleteBinding.inflate(layoutInflater)
    }

}