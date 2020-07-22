package com.destinyed.statussaverpro

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.destinyed.statussaverpro.Fragments.Wsaver
import com.destinyed.statussaverpro.News.NewsFeed
import com.google.android.material.bottomnavigation.BottomNavigationView
import hotchemi.android.rate.AppRate
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var toolbar: androidx.appcompat.widget.Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1){
            checkPermissionNow()
        }

        //Initialize variables here
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, Wsaver()).commit()

        /**
         * Get the bottom navigation view from the main activity layout
         */
        bottomNav.setOnNavigationItemSelectedListener(object : BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                var selectedFragment : Fragment? = null
                when(item.itemId){
                    R.id.wSaver -> selectedFragment = Wsaver()
                    R.id.news -> selectedFragment = NewsFeed()
                    R.id.settings -> selectedFragment = com.destinyed.statussaverpro.Fragments.Settings()
                }

                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, selectedFragment!!).commit()
                return true
            }

        })


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

    override fun onStart() {
        super.onStart()
        checkPermissionNow()
    }

    private fun checkPermissionNow() {
        ActivityCompat.requestPermissions(this@MainActivity, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.menu_main_activity, menu)
//
//        return super.onCreateOptionsMenu(menu)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when(item.itemId){
//            R.id.settings -> startActivity(Intent(baseContext, Settings::class.java))
//        }
//        return super.onOptionsItemSelected(item)
//    }

    private fun toast(message : String){
        Toast.makeText(baseContext, message, Toast.LENGTH_LONG).show()
    }
}
