package com.status.statussaverpro.Fragments


import android.content.Intent
import android.graphics.BitmapFactory
import android.media.ThumbnailUtils
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.status.statussaverpro.Adapters.StatusAdapter
import com.status.statussaverpro.Constants.ConstantsVariables
import com.status.statussaverpro.Models.Models
import com.status.statussaverpro.R
import com.google.android.gms.ads.*
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.nio.channels.FileChannel
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class Images : Fragment() {

    private lateinit var recyclerView: RecyclerView

//    var statusArr = ArrayList<Models>()
    var statusArr = ArrayList<Any>()

    private lateinit var errorStatusImg : LinearLayout

    private lateinit var imageAdapter : StatusAdapter

    private lateinit var extensionImg : String

    private lateinit var openWhatApp : TextView

    ///ads
    val ITEMS_PER_AD: Int = 8

    private val AD_UNIT_ID = "ca-app-pub-1420223449979323/8943795761"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_images, container, false)

        //initialize recycler view here
        recyclerView = root.findViewById(R.id.recyclerView)
        errorStatusImg = root.findViewById(R.id.showErrorImg)

        extensionImg = String()
        openWhatApp = root.findViewById(R.id.openWhatsApp)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //write all code here

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(context, 2)



        if (!ConstantsVariables.whatExits.exists() && !ConstantsVariables.whaSecondtExits.exists()){
            errorStatusImg.visibility = View.VISIBLE
        }
        else{
            getStatus()

            ///Get ads
            addBannerAds();
            loadBannerAds();
        }


        openWhatApp.setOnClickListener {
            showWhatsApp()
        }
    }

    override fun onResume() {
        super.onResume()
        getStatus();

        addBannerAds();
        loadBannerAds();
    }

    /**
     * Adds banner ads to the items list.
     */
    private fun addBannerAds() {
        // Loop through the items array and place a new banner ad in every ith position in
        // the items List.
        var i = 0
        while (i <= statusArr.size) {
            val adView = AdView(context)
            adView.adSize = AdSize.MEDIUM_RECTANGLE
            adView.adUnitId = AD_UNIT_ID
            statusArr.add(i, adView)
            i += ITEMS_PER_AD
        }
    }

    /**
     * Sets up and loads the banner ads.
     */
    private fun loadBannerAds() {
        // Load the first banner ad in the items list (subsequent ads will be loaded automatically
        // in sequence).
        loadBannerAd(0)
    }

    /**
     * Loads the banner ads in the items list.
     */
    private fun loadBannerAd(index: Int) {
        if (index >= statusArr.size) {
            return
        }
        val item: Any = statusArr[index] as? AdView
            ?: throw ClassCastException(
                "Expected item at index " + index + " to be a banner ad"
                        + " ad."
            )
        val adView = item as AdView

        // Set an AdListener on the AdView to wait for the previous banner ad
        // to finish loading before loading the next ad in the items list.
        adView.adListener = object : AdListener() {
            override fun onAdLoaded() {
                super.onAdLoaded()
                // The previous banner ad loaded successfully, call this method again to
                // load the next ad in the items list.
                loadBannerAd(index + ITEMS_PER_AD)
            }

            override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                // The previous banner ad failed to load. Call this method again to load
                // the next ad in the items list.
                val error = String.format(
                    "domain: %s, code: %d, message: %s",
                    loadAdError.domain, loadAdError.code, loadAdError.message
                )
                Log.e(
                    "MainActivity",
                    "The previous banner ad failed to load with error: "
                            + error
                            + ". Attempting to"
                            + " load the next banner ad in the items list."
                )
                loadBannerAd(index + ITEMS_PER_AD)
            }
        }

        // Load the banner ad.
//        adView.loadAd(AdManagerAdRequest.Builder().build())
        adView.loadAd(AdRequest.Builder().build())
    }


    private fun showWhatsApp() {
        try {
            var intent = requireContext().packageManager.getLaunchIntentForPackage("com.whatsapp")
            startActivity(intent)
        }catch (e:Exception){
            Toast.makeText(context, "WhatsApp Can't Be Found On Your Device", Toast.LENGTH_LONG).show()
        }
    }

    private fun getStatus() {

        print(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 11111")

        statusArr.clear()


        if (ConstantsVariables.whatsApp_Path_Dir.exists()){

            print(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> ")

            var listFiles = ConstantsVariables.whatsApp_Path_Dir.listFiles()

            try {
                for (files in listFiles){
                    var getName = files.name
                    var getPath = files.absolutePath

                    var file = files.absoluteFile
                    //var image = Drawable.createFromPath(realPath)
                    extensionImg = getPath.takeLast(4)
                    if (extensionImg == ConstantsVariables.JPG){
                        var image = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(files.absolutePath), ConstantsVariables.THUMBNAILS,
                            ConstantsVariables.THUMBNAILS)

                        statusArr.add(Models(file, getName, getPath, image!!))
                    }

                    else{
                        errorStatusImg.visibility = View.VISIBLE
                    }
                }
            }catch (e:Exception){
                errorStatusImg.visibility = View.VISIBLE
            }


//            if (extensionImg.contains(ConstantsVariables.JPG)){
//                errorStatusImg.visibility = View.GONE
//                imageAdapter = StatusAdapter(context!!, statusArr, Images())
//                recyclerView.adapter = imageAdapter
//                imageAdapter.notifyDataSetChanged()
//            }
//            else{
//                errorStatusImg.visibility = View.VISIBLE
//            }
            if(ConstantsVariables.whatsApp_Path_Dir.exists()){
                var files = ConstantsVariables.whatsApp_Path_Dir.listFiles()

//

                try {
                    for (img in files){
                        var path = img.absolutePath
                        var ext = path.takeLast(4)
                        if (ext == ConstantsVariables.JPG){

                            imageAdapter = StatusAdapter(requireContext(), statusArr, Images())
                            recyclerView.adapter = imageAdapter
                            imageAdapter.notifyDataSetChanged()
                            errorStatusImg.visibility = View.GONE
                        }
                        else{
//                            errorStatusImg.visibility = View.VISIBLE
                        }
                    }
                }catch (e:Exception){
                    e.printStackTrace()
                }
            }



        }


        ///Check second path
        if (ConstantsVariables.whatsApp_Path_second.exists()){

            print(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 44444 ")


            var listFiles = ConstantsVariables.whatsApp_Path_second.listFiles()

            try {
                for (files in listFiles){
                    var getName = files.name
                    var getPath = files.absolutePath

                    var file = files.absoluteFile
                    //var image = Drawable.createFromPath(realPath)
                    extensionImg = getPath.takeLast(4)
                    if (extensionImg == ConstantsVariables.JPG){
                        var image = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(files.absolutePath), ConstantsVariables.THUMBNAILS,
                            ConstantsVariables.THUMBNAILS)

                        statusArr.add(Models(file, getName, getPath, image!!))
                    }

                    else{
                        errorStatusImg.visibility = View.VISIBLE
                    }
                }
            }catch (e:Exception){
                errorStatusImg.visibility = View.VISIBLE
            }


//            if (extensionImg.contains(ConstantsVariables.JPG)){
//                errorStatusImg.visibility = View.GONE
//                imageAdapter = StatusAdapter(context!!, statusArr, Images())
//                recyclerView.adapter = imageAdapter
//                imageAdapter.notifyDataSetChanged()
//            }
//            else{
//                errorStatusImg.visibility = View.VISIBLE
//            }
            if(ConstantsVariables.whatsApp_Path_second.exists()){
                var files = ConstantsVariables.whatsApp_Path_second.listFiles()

                try {
                    for (img in files){
                        var path = img.absolutePath
                        var ext = path.takeLast(4)
                        if (ext == ConstantsVariables.JPG){

                            imageAdapter = StatusAdapter(requireContext(), statusArr, Images())
                            recyclerView.adapter = imageAdapter
                            imageAdapter.notifyDataSetChanged()
                            errorStatusImg.visibility = View.GONE
                        }
                        else{
//                            errorStatusImg.visibility = View.VISIBLE
                        }
                    }
                }catch (e:Exception){
                    e.printStackTrace()
                }
            }



        }



    }

    fun downloadImage(statusDownload: Models) {
        var file = ConstantsVariables.AppDirImage
        if (!file.exists()){
            file.mkdirs()
        }
        var destinationfile = File(file.toString(),statusDownload.title)

        if (!destinationfile.exists()){
            destinationfile.delete()
        }

        try {
            copyFile(statusDownload.file, destinationfile)

            //refresh gallery
            var intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
            intent.data = Uri.fromFile(destinationfile)
            requireActivity().sendBroadcast(intent)

        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    private fun copyFile(title: File, destinationfile: File) {
        if (!destinationfile.parentFile.exists()){
            destinationfile.parentFile.mkdirs()
        }

        if (!destinationfile.exists()){
            destinationfile.createNewFile()
        }

        var source : FileChannel? = null
        var destination : FileChannel? = null

        source = FileInputStream(title).channel
        destination = FileOutputStream(destinationfile).channel

        destination.transferFrom(source, 0, source.size())

        source.close()
        destination.close()


    }

}

//done
/**
 * DESTINY ED
 * GMAIL : dikeachaeze@gmail.com
 * WED 03 JUNE 2020
 */
