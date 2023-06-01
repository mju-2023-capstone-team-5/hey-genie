package org.sopar.presentation.reservation

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.sopar.R
import org.sopar.data.remote.response.Notice
import org.sopar.databinding.DialogCommentBinding
import org.sopar.databinding.DialogCommentListBinding
import org.sopar.databinding.DialogNoticeBinding
import org.sopar.databinding.DialogReservationDeleteBinding
import org.sopar.presentation.base.BaseDialog
import org.sopar.presentation.myReservation.CommentDialog
import org.sopar.presentation.myReservation.MyReservationDirections
import org.sopar.presentation.reservationDetail.ReservationDetailViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CommentListDialog(private val reservationViewModel: ReservationViewModel, private val parkingLotId: Int): BaseDialog<DialogCommentListBinding>(R.layout.dialog_comment_list){
    val commentAdapter: CommentAdapter = CommentAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        setRecyclerViewAdapter()
        observe()
        init()

        return binding.root
    }

    private fun observe() {
        reservationViewModel.commentList.observe(viewLifecycleOwner) { commentList ->
            if (commentList.isNotEmpty()) {
                binding.listComment.visibility = View.VISIBLE
                binding.textNoComment.visibility = View.GONE
                commentAdapter.submitList(commentList.toMutableList())
            } else {
                binding.listComment.visibility = View.GONE
                binding.textNoComment.visibility = View.VISIBLE
            }
        }
    }

    private fun init() {
        reservationViewModel.getCommentByPKId(parkingLotId)

        binding.btnOk.setOnClickListener {
            dismiss()
        }
    }

    override fun getViewBinding(): DialogCommentListBinding {
        return DialogCommentListBinding.inflate(layoutInflater)
    }

    private fun setRecyclerViewAdapter() {
        binding.listComment.apply {
            setHasFixedSize(true)
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = commentAdapter
        }
    }

}