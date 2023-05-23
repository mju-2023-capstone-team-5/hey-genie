package org.sopar.presentation.map

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.daum.mf.map.api.CalloutBalloonAdapter
import net.daum.mf.map.api.MapPOIItem
import org.sopar.R

class CustomBalloonAdapter(inflater: LayoutInflater): CalloutBalloonAdapter {
    private val mCalloutBalloon = inflater.inflate(R.layout.bolloon_layout, null)

    override fun getCalloutBalloon(poiItem: MapPOIItem?): View {
        // 마커 클릭 시 나오는 말풍선
        val nameText = mCalloutBalloon.findViewById<TextView>(R.id.text_parking_lot_name)
        nameText.text = poiItem?.itemName
        return mCalloutBalloon
    }

    override fun getPressedCalloutBalloon(p0: MapPOIItem?): View {
        CoroutineScope(Dispatchers.Main).launch {
            onItemClickListener?.let {
                it(p0?.tag!!)
            }
        }
        return mCalloutBalloon
    }

    private var onItemClickListener: ((Int) -> Unit)? = null

    fun setOnItemClickListener(listener: (Int) -> Unit) {
        onItemClickListener = listener
    }
}