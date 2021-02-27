package com.applications.all_possibilities.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.applications.all_possibilities.Data.ViewPagerItem

class ViewPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    private  val fragments = ArrayList<Fragment>()
    private val titles = ArrayList<String>()

    override fun getCount(): Int {
       return fragments.size
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    fun addFragment(viewPagerItem: ViewPagerItem){
        fragments.add(viewPagerItem.fragment)
        titles.add(viewPagerItem.title)
        notifyDataSetChanged()
    }

    override fun getPageTitle(position: Int): CharSequence {
        return titles[position]
    }


}