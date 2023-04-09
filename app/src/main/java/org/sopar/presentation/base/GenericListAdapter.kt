package org.sopar.presentation.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.recyclerview.widget.ListAdapter

class GenericListAdapter<T: Any>(
    @IdRes val layoutResId: Int,
    inline val bind: (item: T, holder: BaseViewHolder, itemCount: Int) -> Unit
): ListAdapter<T, BaseViewHolder>(BaseDiffCallback<T>()) {
    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        bind(getItem(position), holder, itemCount)
    }

    override fun getItemViewType(position: Int) = layoutResId

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val root = LayoutInflater.from(parent.context).inflate(
            viewType, parent, false
        )

        return BaseViewHolder(root as ViewGroup)
    }

    override fun getItemCount() = currentList.size
}