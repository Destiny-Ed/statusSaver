package com.status.statussaverpro.Adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.status.statussaverpro.Fragments.Saved_Images
import com.status.statussaverpro.Fragments.Saved_Videos

class SavedFragmentAdapter(fm : FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fm, lifecycle) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment : Fragment? = null

        when(position){
            0 -> fragment = Saved_Images()
            else -> fragment = Saved_Videos()
        }

        return fragment

    }
}