package org.sopar.presentation.myReservation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.ListAdapter
import org.sopar.R
import org.sopar.data.remote.response.ParkingLot
import org.sopar.data.remote.response.ReservationPreview
import org.sopar.databinding.ItemMyParkingLotBinding
import org.sopar.databinding.ItemReservationBinding
import org.sopar.presentation.base.BaseDiffCallback

class MyReservationAdapter: ListAdapter<ReservationPreview, MyReservationViewHolder>(BaseDiffCallback<ReservationPreview>()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyReservationViewHolder {
        return MyReservationViewHolder(ItemReservationBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyReservationViewHolder, position: Int) {
        val reservation = currentList[position]
        holder.bind(reservation)
        holder.itemView.setOnClickListener {
            onItemClickListener.let {
                onItemClickListener?.let { it(reservation) }
            }
        }

        holder.itemView.findViewById<AppCompatButton>(R.id.btn_reservation_comment).setOnClickListener {
            onCommentBtnClickListener.let {
                onCommentBtnClickListener?.let { it(reservation) }
            }
        }
    }

    private var onItemClickListener: ((ReservationPreview) -> Unit)? = null
    fun setOnItemClickListener(listener: (ReservationPreview) -> Unit) {
        onItemClickListener = listener
    }

    private var onCommentBtnClickListener: ((ReservationPreview) -> Unit)? = null
    fun setOnCommentBtnClickListener(listener: (ReservationPreview) -> Unit) {
        onCommentBtnClickListener = listener
    }

    override fun getItemCount() = currentList.size
}