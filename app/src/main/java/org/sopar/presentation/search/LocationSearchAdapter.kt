package org.sopar.presentation.search

import android.annotation.SuppressLint
import org.sopar.R
import org.sopar.data.remote.response.Place
import org.sopar.presentation.base.BaseViewHolder
import org.sopar.presentation.base.GenericListAdapter

@SuppressLint("ResourceType")
class LocationSearchAdapter(bind: (Place, BaseViewHolder, Int) -> Unit) : GenericListAdapter<Place>(R.layout.item_location, bind) {
}