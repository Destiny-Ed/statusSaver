//package com.destinyed.statussaverpro.Adapters
//
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.FragmentManager
//import androidx.lifecycle.Lifecycle
//import androidx.viewpager2.adapter.FragmentStateAdapter
//import com.destinyed.statussaverpro.Fragments.Images
//import com.destinyed.statussaverpro.Fragments.Saved_Status
//import com.destinyed.statussaverpro.Fragments.Videos
//
//class FragmentAdapter(fm:FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fm, lifecycle) {
//    override fun getItemCount(): Int {
//        return 3
//    }
//
//    override fun createFragment(position: Int): Fragment {
//        var fragment : Fragment? = null
//
//        when(position){
//            0 -> fragment = Images()
//            1-> fragment = Videos()
//            else -> fragment = Saved_Status()
//        }
//
//        return fragment
//    }
//
//
//
//}

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.status.statussaverpro.Fragments.Images
import com.status.statussaverpro.Fragments.Saved_Status
import com.status.statussaverpro.Fragments.Videos

internal class MyAdapter(var context: Context, fm: FragmentManager?, var totalTabs: Int) :
    FragmentPagerAdapter(fm!!) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                Images()
            }
            1 -> {
                Videos()
            }
            else -> {
                Saved_Status()
            }

        }
    }

    override fun getCount(): Int {
        return totalTabs
    }
}