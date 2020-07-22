package com.destinyed.statussaverpro.Fragments

import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TableLayout
import android.widget.Toast
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.destinyed.statussaverpro.Adapters.FragmentAdapter
import com.destinyed.statussaverpro.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_wsaver.*


class Wsaver : Fragment() {

    private lateinit var viewPager : ViewPager2
    private lateinit var adapter : FragmentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_wsaver, container, false)
        var progress = root.findViewById<ProgressBar>(R.id.progress)
        progress.visibility = View.VISIBLE
        /**
         * Initialize the global variables
         */
        viewPager = root.findViewById(R.id.viewPager)

        adapter = FragmentAdapter(fragmentManager!!, lifecycle)
        viewPager.adapter = adapter
        var tab = root.findViewById<TabLayout>(R.id.tabLayout)

        TabLayoutMediator(tab, viewPager, TabLayoutMediator.TabConfigurationStrategy {tab, position ->
            when (position){
                0 -> tab.text = "Images"
                1 -> tab.text = "Videos"
                else -> tab.text = "Saved Status"
            }

        }).attach()

        return root
    }

}