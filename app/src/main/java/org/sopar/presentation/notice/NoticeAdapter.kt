package org.sopar.presentation.notice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import org.sopar.data.remote.response.Notice
import org.sopar.databinding.ItemNoticeBinding
import org.sopar.presentation.base.BaseDiffCallback

class NoticeAdapter: ListAdapter<Notice, NoticeViewHolder>(BaseDiffCallback<Notice>()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticeViewHolder {
        return NoticeViewHolder(ItemNoticeBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: NoticeViewHolder, position: Int) {
        val notice = currentList[position]
        holder.bind(notice)
        holder.itemView.setOnClickListener {
            onItemClickListener?.let { it(notice) }
        }
    }

    private var onItemClickListener: ((Notice) -> Unit)? = null
    fun setOnItemClickListener(listener: (Notice) -> Unit) {
        onItemClickListener = listener
    }

    override fun getItemCount() = currentList.size

}