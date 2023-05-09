package org.sopar.presentation.notice

import org.sopar.data.remote.response.Notice
import org.sopar.databinding.ItemNoticeBinding
import org.sopar.presentation.base.BaseViewHolder

class NoticeViewHolder(
    private val binding: ItemNoticeBinding
): BaseViewHolder<Notice>(binding.root) {

    override fun bind(item: Notice) {
        binding.textNoticeTitle.text = item.title
        binding.textNoticeContent.text = item.content
    }
}