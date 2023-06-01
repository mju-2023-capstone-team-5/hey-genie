package org.sopar.presentation.myReservation

import android.view.View
import org.sopar.data.remote.response.ReservationPreview
import org.sopar.databinding.ItemReservationBinding
import org.sopar.presentation.base.BaseViewHolder
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MyReservationViewHolder(
    private val binding: ItemReservationBinding
): BaseViewHolder<ReservationPreview>(binding.root){

    override fun bind(item: ReservationPreview) {
        binding.textName.text = item.parkingLotName
        val startTime = LocalDateTime.parse(item.startTime, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"))
        binding.textStartTime.text = startTime.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분"))
        val endTime = LocalDateTime.parse(item.endTime, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"))
        binding.textEndTime.text = endTime.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분"))
        if (item.reviewWritten == true) {
            binding.btnReservationComment.visibility = View.INVISIBLE
        }
    }

}