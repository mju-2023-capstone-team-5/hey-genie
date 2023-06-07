package org.sopar.presentation.myParkingLot

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import org.sopar.data.remote.response.ParkingLot
import org.sopar.databinding.ItemMyParkingLotBinding
import org.sopar.presentation.base.BaseDiffCallback

class MyParkingLotAdapter: ListAdapter<ParkingLot, MyParkingLotViewHolder>(BaseDiffCallback<ParkingLot>()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyParkingLotViewHolder {
        return MyParkingLotViewHolder(ItemMyParkingLotBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyParkingLotViewHolder, position: Int) {
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