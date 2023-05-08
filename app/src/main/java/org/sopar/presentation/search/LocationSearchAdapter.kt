package org.sopar.presentation.search

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import org.sopar.data.remote.response.Place
import org.sopar.databinding.ItemLocationBinding
import org.sopar.presentation.base.BaseDiffCallback

class LocationSearchAdapter: ListAdapter<Place, LocationSearchViewHolder>(BaseDiffCallback<Place>()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationSearchViewHolder {
        return LocationSearchViewHolder(ItemLocationBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: LocationSearchViewHolder, position: Int) {
        val place = currentList[position]
        holder.bind(place)
        holder.itemView.setOnClickListener {
            onItemClickListener?.let { it(place) }
        }
    }

    private var onItemClickListener: ((Place) -> Unit)? = null
    fun setOnItemClickListener(listener: (Place) -> Unit) {
        onItemClickListener = listener
    }

    override fun getItemCount() = currentList.size
}