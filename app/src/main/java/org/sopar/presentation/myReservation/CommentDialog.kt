package org.sopar.presentation.myReservation

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.sopar.R
import org.sopar.data.remote.response.Notice
import org.sopar.databinding.DialogCommentBinding
import org.sopar.databinding.DialogNoticeBinding
import org.sopar.databinding.DialogReservationDeleteBinding
import org.sopar.presentation.base.BaseDialog
import org.sopar.presentation.reservationDetail.ReservationDetailViewModel

class CommentDialog(private val myReservationViewModel: MyReservationViewModel, private val reservationId: Int): BaseDialog<DialogCommentBinding>(R.layout.dialog_comment){

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        observe()
        init()
        setCommentListener()
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setCommentListener() {
        binding.btnOk.setOnClickListener {
            val rating = binding.reservationRatingBar.rating
            val comment = binding.textCommentContent.text.toString()

            if (! comment.isNullOrEmpty()) {
                myReservationViewModel.registerParkingLotGrade(reservationId, comment, rating)
                dismiss()
            } else {
                Toast.makeText(context, "후기도 함께 작성해주세요!🙏", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun observe() {
        myReservationViewModel.reservation.observe(viewLifecycleOwner) { reservation ->
            if (reservation.isReviewWritten) {
                Toast.makeText(context, "후기를 이미 작성했어요!", Toast.LENGTH_SHORT).show()
                binding.btnOk.isClickable = false
            }
        }
    }

    private fun init() {
        CoroutineScope(Dispatchers.IO).launch {
            myReservationViewModel.getReservationById(reservationId)
        }
    }

    override fun getViewBinding(): DialogCommentBinding {
        return DialogCommentBinding.inflate(layoutInflater)
    }

}