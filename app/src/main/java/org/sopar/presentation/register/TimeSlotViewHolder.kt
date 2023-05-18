package org.sopar.presentation.register

import org.sopar.data.remote.response.TimeSlot
import org.sopar.databinding.ItemTimeSlotBinding
import org.sopar.presentation.base.BaseViewHolder

class TimeSlotViewHolder(
    private val binding: ItemTimeSlotBinding
): BaseViewHolder<TimeSlot>(binding.root) {

    override fun bind(item: TimeSlot) {
        binding.textStartTimeSlot.setText("${item.startTime}:${item.startMinute}")
        binding.textEndTimeSlot.setText("${item.endTime}:${item.endMinute}")
    }
}