package org.sopar.presentation.map

import com.bumptech.glide.Glide
import org.sopar.data.remote.response.ParkingLot
import org.sopar.databinding.ItemParkingLotBinding
import org.sopar.presentation.base.BaseViewHolder

class ParkingLotViewHolder(
    private val binding: ItemParkingLotBinding
): BaseViewHolder<ParkingLot>(binding.root) {

    override fun bind(item: ParkingLot) {
        binding.textName.text = item.name
        binding.textAddress.text = item.address
        binding.textRemaining.text = item.remainingSpace.toString()
        item.imageUrl?.let {
            Glide.with(binding.root).load(item.imageUrl).into(binding.imageParkingLot)
        }
        item.reviewSummary?.let {
            binding.parkingLotComment.text = it
        }
        binding.parkingLotRating.rating = item.ratingAvg!!.toFloat()
    }

}