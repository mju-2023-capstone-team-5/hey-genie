package org.sopar.presentation.register

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class RegisterRecyclerViewAdapter(
    private val fragmentActivity: FragmentActivity
): FragmentStateAdapter(fragmentActivity) {
    private val fragList: ArrayList<Fragment> = ArrayList()

    override fun getItemCount(): Int {
        return fragList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragList[position]
    }

    fun addFragment(fragment: Fragment) {
        fragList.add(fragment)
        notifyItemInserted(fragList.size-1)
    }

}