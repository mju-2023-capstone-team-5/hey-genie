package org.sopar.presentation.reservation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.ListAdapter
import org.sopar.R
import org.sopar.data.remote.request.Grade
import org.sopar.data.remote.response.ParkingLot
import org.sopar.data.remote.response.ReservationPreview
import org.sopar.databinding.ItemCommentBinding
import org.sopar.databinding.ItemMyParkingLotBinding
import org.sopar.databinding.ItemReservationBinding
import org.sopar.presentation.base.BaseDiffCallback

class CommentAdapter: ListAdapter<Grade, CommentViewHolder>(BaseDiffCallback<Grade>()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        return CommentViewHolder(ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val grade = currentList[position]
        holder.bind(grade)
    }

    override fun getItemCount() = currentList.size
}