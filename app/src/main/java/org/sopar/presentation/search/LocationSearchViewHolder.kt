package org.sopar.presentation.search

import org.sopar.databinding.ItemLocationBinding
import org.sopar.presentation.base.BaseViewHolder

class LocationSearchViewHolder(
    private val binding: ItemLocationBinding
): BaseViewHolder(binding.root) {

    fun bind(place: String, address: String) {
        binding.textBuildingName.text = place
        binding.textRoadAddress.text = address
    }
}