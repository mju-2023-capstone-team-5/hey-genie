package org.sopar.presentation.reservation

import org.sopar.data.remote.request.Grade
import org.sopar.databinding.ItemCommentBinding
import org.sopar.presentation.base.BaseViewHolder

class CommentViewHolder(
    private val binding: ItemCommentBinding
): BaseViewHolder<Grade>(binding.root){

    override fun bind(item: Grade) {
        binding.parkingLotRating.rating = item.rating
        binding.parkingLotComment.text = item.comment
    }

}