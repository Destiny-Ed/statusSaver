package com.destinyed.statussaverpro

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.destinyed.statussaverpro.Adapters.FragmentAdapter
import com.destinyed.statussaverpro.splashScreen_and_Settings_utils.Settings
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var viewPager: ViewPager2
    private lateinit var adapter : FragmentAdapter

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
        adapter = FragmentAdapter(supportFragmentManager, lifecycle)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager, TabLayoutMediator.TabConfigurationStrategy {tab, position ->
            when (position){
                0 -> tab.text = "Images"
                1 -> tab.text = "Videos"
                else -> tab.text = "Saved Status"
            }

        }).attach()



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
            R.id.settings -> startActivity(Intent(baseContext, Settings::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    private fun toast(message : String){
        Toast.makeText(baseContext, message, Toast.LENGTH_LONG).show()
    }
}
