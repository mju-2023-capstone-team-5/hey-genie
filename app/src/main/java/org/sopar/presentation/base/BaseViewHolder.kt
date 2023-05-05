package org.sopar.presentation.base

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

open class BaseViewHolder<T>(container: ViewGroup): RecyclerView.ViewHolder(container){
    open fun bind(item: T) {}
}