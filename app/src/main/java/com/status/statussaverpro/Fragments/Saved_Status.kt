package com.status.statussaverpro.Fragments


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.status.statussaverpro.Adapters.SavedFragmentAdapter

import com.status.statussaverpro.R

/**
 * A simple [Fragment] subclass.
 */
class Saved_Status : Fragment() {

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager : ViewPager2
    private lateinit var savedFragmentAdapter : SavedFragmentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_saved__status, container, false)

        tabLayout = root.findViewById(R.id.tabLayoutSaved)
        viewPager = root.findViewById(R.id.pagerSaved)

        //instantiate SavedFragmentAdapter here
        savedFragmentAdapter = SavedFragmentAdapter(fragmentManager!!, lifecycle)
        viewPager.adapter = savedFragmentAdapter

        TabLayoutMediator(tabLayout, viewPager, TabLayoutMediator.TabConfigurationStrategy {tab, position ->
            when(position){
                0 -> tab.text = "Saved Images"
                else -> tab.text = "Saved Videos"
            }
        }).attach()

        //Inflate layout here
        return root
    }
}