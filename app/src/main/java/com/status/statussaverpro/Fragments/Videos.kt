package com.status.statussaverpro.Fragments


import android.media.ThumbnailUtils
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.status.statussaverpro.Adapters.Video_Statust_Adapter
import com.status.statussaverpro.Constants.ConstantsVariables
import com.status.statussaverpro.Models.VideoModel
import com.status.statussaverpro.R
import com.google.android.gms.ads.*
import java.lang.Exception

/**
 * A simple [Fragment] subclass.
 */
class Videos : Fragment() {

    private lateinit var recyclerView: RecyclerView

    var statusArr = ArrayList<Any>()
    private lateinit var videoAdapter : Video_Statust_Adapter

    private lateinit var errorStatusVid : LinearLayout

    private lateinit var extensionVid : String
    private lateinit var showWhatApp : TextView

    ///ads
    val ITEMS_PER_AD: Int = 8

    private val AD_UNIT_ID = "ca-app-pub-3940256099942544/6300978111"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_videos, container, false)

        //initialize recycler view here
        recyclerView = root.findViewById(R.id.recyclerView)
        extensionVid = String()
        showWhatApp = root.findViewById(R.id.openWhatsApp)
        errorStatusVid = root.findViewById(R.id.showErrorVid)
        return root
    }

//    override fun onResume() {
//        super.onResume()
//
//        getStatus()
//    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(context, 2)

        if (!ConstantsVariables.whatExits.exists() && !ConstantsVariables.whaSecondtExits.exists()){
            errorStatusVid.visibility = View.VISIBLE
        }else{
            getStatus()

            ///Get ads
            addBannerAds();
            loadBannerAds();
        }



        showWhatApp.setOnClickListener {
            openWhatsApp()
        }
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

    private fun openWhatsApp() {
        try {
            val intent = requireContext().packageManager.getLaunchIntentForPackage("com.whatsapp")
            startActivity(intent)
        }catch (e:Exception){
            Toast.makeText(context, "WhatsApp Can't Be Found On Your Device", Toast.LENGTH_LONG).show()
        }
    }

    private fun getStatus() {

        if (ConstantsVariables.whatsApp_Path_DirVideo.exists()){

            var listFiles = ConstantsVariables.whatsApp_Path_DirVideo.listFiles()

            try {
                for (files in listFiles){
                    var getName = files.name
                    var getPath = files.path

                    var file = files.absoluteFile

                    var video = ThumbnailUtils.createVideoThumbnail(files.absolutePath, MediaStore.Images.Thumbnails.MICRO_KIND)
                    extensionVid = getPath.takeLast(4)

                    if (extensionVid == ConstantsVariables.MP4){
                            statusArr.add(VideoModel(file, getName, getPath, video!!))

                    }
                    else{
                        errorStatusVid.visibility = View.VISIBLE
                    }
                }
            }catch (e:Exception){
                errorStatusVid.visibility = View.VISIBLE
            }

            if(ConstantsVariables.whatsApp_Path_DirVideo.exists()){
                var files = ConstantsVariables.whatsApp_Path_DirVideo.listFiles()

                try {
                    for (app in files){
                        var path = app.absolutePath
                        var ext = path.takeLast(4)
                        if (ext == ConstantsVariables.MP4){
                            videoAdapter = Video_Statust_Adapter(requireActivity(), statusArr, Videos())
                            recyclerView.adapter = videoAdapter
                            videoAdapter.notifyDataSetChanged()
                            errorStatusVid.visibility = View.GONE

                        }
                        else{

                        }
                    }
                }catch (e:Exception){
                    e.printStackTrace()
                }
            }




        }

        ///Check if second path exists
        if (ConstantsVariables.whatsApp_Path_second.exists()){

            var listFiles = ConstantsVariables.whatsApp_Path_second.listFiles()

            try {
                for (files in listFiles){
                    var getName = files.name
                    var getPath = files.path

                    var file = files.absoluteFile

                    var video = ThumbnailUtils.createVideoThumbnail(files.absolutePath, MediaStore.Images.Thumbnails.FULL_SCREEN_KIND)
                    extensionVid = getPath.takeLast(4)

                    if (extensionVid == ConstantsVariables.MP4){
                        statusArr.add(VideoModel(file, getName, getPath, video!!))
                    }
                    else{
                        errorStatusVid.visibility = View.VISIBLE
                    }
                }
            }catch (e:Exception){
                errorStatusVid.visibility = View.VISIBLE
            }

            if(ConstantsVariables.whatsApp_Path_second.exists()){
                var files = ConstantsVariables.whatsApp_Path_second.listFiles()

                try {
                    for (app in files){
                        var path = app.absolutePath
                        var ext = path.takeLast(4)
                        if (ext == ConstantsVariables.MP4){
                            videoAdapter = Video_Statust_Adapter(requireActivity(), statusArr, Videos())
                            recyclerView.adapter = videoAdapter
                            videoAdapter.notifyDataSetChanged()
                            errorStatusVid.visibility = View.GONE

                        }
                        else{

                        }
                    }
                }catch (e:Exception){
                    e.printStackTrace()
                }
            }




        }
    }

//    fun downloadVideo(statusDownload: VideoModel) {
//        var file = ConstantsVariables.AppDirVideo
//        if (!file.exists()){
//            file.mkdirs()
//        }
//        var destinationfile = File(file.toString(), statusDownload.title)
//
//        if (!destinationfile.exists()){
//            destinationfile.delete()
//        }
//
//        try {
//            copyFile(statusDownload.file, destinationfile)
//
//            //refresh gallery
//            var intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
//            intent.data = Uri.fromFile(destinationfile)
//            activity!!.sendBroadcast(intent)
//
//        }catch (e: Exception){
//            e.printStackTrace()
//        }
//    }


//    private fun copyFile(file: File, destinationfile: File) {
//
//        if (!destinationfile.parentFile.exists()){
//            destinationfile.parentFile.mkdirs()
//        }
//
//        if (!destinationfile.exists()){
//            destinationfile.createNewFile()
//        }
//
//        var source : FileChannel? = null
//        var destination : FileChannel? = null
//
//        source = FileInputStream(file).channel
//        destination = FileOutputStream(destinationfile).channel
//
//        destination.transferFrom(source, 0, source.size())
//
//        source.close()
//        destination.close()
//
//
//    }

}


