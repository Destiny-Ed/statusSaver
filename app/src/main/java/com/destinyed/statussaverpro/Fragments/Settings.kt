package com.destinyed.statussaverpro.Fragments

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.destinyed.statussaverpro.R

class Settings : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_settings, container, false)

        var share = root.findViewById<LinearLayout>(R.id.share)
        var rate = root.findViewById<LinearLayout>(R.id.rate)

        share.setOnClickListener {
            shareApp()
        }

        rate.setOnClickListener {
            rateApp()
        }



        return root
    }

    private fun shareApp() {
        var intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"

        var uri = "Save your friends images and videos without asking them for it. No Internet Required" +
                " https://play.google.com/store/apps/details?id=com.destinyed.statussaverpro"
        intent.putExtra(Intent.EXTRA_TEXT, uri)
        startActivity(Intent.createChooser(intent, "Share StatusSaverPro"))
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

}