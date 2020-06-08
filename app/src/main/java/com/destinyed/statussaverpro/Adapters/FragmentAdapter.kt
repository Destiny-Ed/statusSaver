package com.destinyed.statussaverpro.Adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.destinyed.statussaverpro.Fragments.Images
import com.destinyed.statussaverpro.Fragments.Saved_Status
import com.destinyed.statussaverpro.Fragments.Videos

class FragmentAdapter(fm : FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fm, lifecycle) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        var fragment : Fragment? = null

        when(position){
            0 -> fragment = Images()
            1-> fragment = Videos()
            else -> fragment = Saved_Status()
        }

        return fragment
    }



}