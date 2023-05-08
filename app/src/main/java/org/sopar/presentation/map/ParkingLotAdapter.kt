package org.sopar.presentation.map

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import org.sopar.data.remote.response.ParkingLot
import org.sopar.data.remote.response.Place
import org.sopar.databinding.ItemParkingLotBinding
import org.sopar.presentation.base.BaseDiffCallback

class ParkingLotAdapter: ListAdapter<ParkingLot, ParkingLotViewHolder>(BaseDiffCallback<ParkingLot>()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParkingLotViewHolder {
        return ParkingLotViewHolder(ItemParkingLotBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ParkingLotViewHolder, position: Int) {
        val parkingLot = currentList[position]
        holder.bind(parkingLot)
        holder.itemView.setOnClickListener {
            onItemClickListener.let {
                onItemClickListener?.let { it(parkingLot) }
            }
        }
    }

    private var onItemClickListener: ((ParkingLot) -> Unit)? = null
    fun setOnItemClickListener(listener: (ParkingLot) -> Unit) {
        onItemClickListener = listener
    }

    override fun getItemCount() = currentList.size
}