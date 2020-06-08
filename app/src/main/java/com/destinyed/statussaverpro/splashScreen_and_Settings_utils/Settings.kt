package com.destinyed.statussaverpro.splashScreen_and_Settings_utils

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.destinyed.statussaverpro.R

class Settings : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        var share = findViewById<LinearLayout>(R.id.share)
        var rate = findViewById<LinearLayout>(R.id.rate)
        var backBtn = findViewById<ImageView>(R.id.goBack)

        backBtn.setOnClickListener {
            onBackPressed()
        }

        share.setOnClickListener {
            shareApp()
        }

        rate.setOnClickListener {
            rateApp()
        }
    }

    private fun rateApp() {
        //open with playstore app if installed
        try {
            var path = "market://details?id=com.destinyed.statussaverpro"
            var uri = Uri.parse(path)
            var intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)

        } catch (e: ActivityNotFoundException) {
            //open with browser if playstore app is not installed
            var path = "https://play.google.com/store/apps/details?id=com.destinyed.statussaverpro"
            var uri = Uri.parse(path)
            var intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
    }

    private fun shareApp() {
        var intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"

        var uri = "Save your friends images and videos without asking them for it. No Internet Required" +
                " https://play.google.com/store/apps/details?id=com.destinyed.statussaverpro"
        intent.putExtra(Intent.EXTRA_TEXT, uri)
        startActivity(Intent.createChooser(intent, "Share StatusSaverPro"))
    }
}
