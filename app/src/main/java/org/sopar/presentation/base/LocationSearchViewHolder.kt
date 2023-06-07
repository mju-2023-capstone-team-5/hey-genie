package org.sopar.presentation.base

import androidx.recyclerview.widget.RecyclerView
import org.sopar.data.remote.response.Place
import org.sopar.databinding.ItemLocationBinding
import org.sopar.presentation.base.BaseViewHolder


class LocationSearchViewHolder(
    private val binding: ItemLocationBinding
): BaseViewHolder<Place>(binding.root) {

    override fun bind(item: Place) {
        binding.textBuildingName.text = item.place_name
        binding.textRoadAddress.text = item.road_address_name
    }

}