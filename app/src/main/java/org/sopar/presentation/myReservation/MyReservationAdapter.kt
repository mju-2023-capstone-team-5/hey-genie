package org.sopar.presentation.myReservation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
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
    }

    private var onItemClickListener: ((ReservationPreview) -> Unit)? = null
    fun setOnItemClickListener(listener: (ReservationPreview) -> Unit) {
        onItemClickListener = listener
    }

    override fun getItemCount() = currentList.size
}