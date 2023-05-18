package org.sopar.presentation.register

import org.sopar.presentation.notice.NoticeViewHolder
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import org.sopar.data.remote.response.TimeSlot
import org.sopar.databinding.ItemTimeSlotBinding
import org.sopar.presentation.base.BaseDiffCallback

class TimeSlotRecyclerViewAdapter: ListAdapter<TimeSlot, TimeSlotViewHolder>(BaseDiffCallback<TimeSlot>()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeSlotViewHolder {
        return TimeSlotViewHolder(ItemTimeSlotBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: TimeSlotViewHolder, position: Int) {
        val timeSlot = currentList[position]
        holder.bind(timeSlot)
        holder.itemView.setOnClickListener {
            onItemClickListener?.let { it(timeSlot) }
        }
    }

    private var onItemClickListener: ((TimeSlot) -> Unit)? = null
    fun setOnItemClickListener(listener: (TimeSlot) -> Unit) {
        onItemClickListener = listener
    }

    override fun getItemCount() = currentList.size

}