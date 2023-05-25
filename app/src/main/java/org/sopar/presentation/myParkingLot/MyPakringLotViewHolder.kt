package org.sopar.presentation.myParkingLot

import com.bumptech.glide.Glide
import org.sopar.data.remote.response.ParkingLot
import org.sopar.databinding.ItemMyParkingLotBinding
import org.sopar.presentation.base.BaseViewHolder

class MyParkingLotViewHolder(
    private val binding: ItemMyParkingLotBinding
): BaseViewHolder<ParkingLot>(binding.root){

    override fun bind(item: ParkingLot) {
        binding.textName.text = item.name
        binding.textAddress.text = item.address
        binding.textRemaining.text = item.remainingSpace.toString()
        item.imageUrl?.let {
            Glide.with(binding.root).load(item.imageUrl).into(binding.imageParkingLot)
        }
    }

}