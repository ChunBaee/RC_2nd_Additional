package com.jcorp.rc_additional_2.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class PagerAdapter(fragmentActivity : FragmentActivity) :  FragmentStateAdapter(fragmentActivity){

    var fragList = mutableListOf<Fragment>()

    fun addItem(frag : Fragment) {
        fragList.add(frag)
    }

    override fun getItemCount(): Int {
        return fragList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragList[position]
    }
}