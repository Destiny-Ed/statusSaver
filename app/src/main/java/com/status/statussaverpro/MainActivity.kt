package com.status.statussaverpro

import MyAdapter
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayout.TabLayoutOnPageChangeListener
import hotchemi.android.rate.AppRate


class MainActivity : AppCompatActivity() {

    private lateinit var toolbar: androidx.appcompat.widget.Toolbar

    private lateinit var viewPager : ViewPager
//    private lateinit var adapter : MyAdapter

    private var mNavigationDrawerItemTitles: Array<String>? = null
    private var mDrawerLayout: DrawerLayout? = null
    private var mDrawerList: ListView? = null
    private var mDrawerTitle: CharSequence? = null
    private var mTitle: CharSequence? = null
    var mDrawerToggle: DrawerLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1){
            checkPermissionNow()
        }

        //Initialize variables here
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        viewPager = findViewById(R.id.viewPager)

//        adapter = FragmentAdapter(fragmentManager, lifecycle)

        var tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        tabLayout.addTab(tabLayout.newTab().setText("Images"))
        tabLayout.addTab(tabLayout.newTab().setText("Videos"))
        tabLayout.addTab(tabLayout.newTab().setText("Saved"))
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL)

        val adapter = MyAdapter(
            this, supportFragmentManager,
            tabLayout.getTabCount()
        )
        viewPager.adapter = adapter

        viewPager.addOnPageChangeListener(TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })


//        TabLayoutMediator(tab, viewPager, TabLayoutMediator.TabConfigurationStrategy { tab, position ->
//            when (position){
//                0 -> tab.text = "Images"
//                1 -> tab.text = "Videos"
//                else -> tab.text = "Saved Status"
//            }
//
//        }).attach()

//        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, Wsaver()).commit()

        /**
         * Get the bottom navigation view from the main activity layout
         */
//        bottomNav.setOnNavigationItemSelectedListener(object : BottomNavigationView.OnNavigationItemSelectedListener {
//            override fun onNavigationItemSelected(item: MenuItem): Boolean {
//                var selectedFragment : Fragment? = null
//                when(item.itemId){
//                    R.id.wSaver -> {
//                        selectedFragment = Wsaver()
//                        item.isEnabled = false
//                        handleClick(item)
//                    }
//                }
//
//                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, selectedFragment!!).commit()
//                return true
//            }
//
//        })


        /**
         * This will always ask user to review app
         */
        AppRate.with(this)
            .setInstallDays(0)
            .setLaunchTimes(3)
            .setRemindInterval(2)
            .monitor()

        AppRate.showRateDialogIfMeetsConditions(this)



    }

    private fun handleClick(selected : MenuItem) {

        Handler().postDelayed({
            //function
            selected.isEnabled = true

        }, 3000.toLong())
    }

    override fun onStart() {
        super.onStart()
        checkPermissionNow()
    }

    private fun checkPermissionNow() {
        ActivityCompat.requestPermissions(this@MainActivity, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main_activity, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
//            R.id.settings -> startActivity(Intent(baseContext, Settings::class.java))
            R.id.action_share -> {
                ///Show dialog
                shareApp()
            }

            R.id.action_about -> {
                ///Show dialog
                val builder = AlertDialog.Builder(this)
                builder.setTitle("About")
                builder.setMessage("Status Saver app allows you to view, download, print and share whatsApp status")
                builder.show()
            }

            R.id.action_rate -> {
                ///Show dialog
            }


        }
        return super.onOptionsItemSelected(item)
    }

    private fun shareApp() {
        var arr: Any = arrayOf(Uri.parse("share app"), "hello")
        var intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/*"

        intent.putExtra(Intent.EXTRA_TEXT, Uri.parse("Share App"))
        startActivity(Intent.createChooser(intent, "Share Image"))
    }

    private fun toast(message : String){
        Toast.makeText(baseContext, message, Toast.LENGTH_LONG).show()
    }
}
