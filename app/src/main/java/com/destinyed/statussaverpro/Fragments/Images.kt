package com.destinyed.statussaverpro.Fragments


import android.content.Intent
import android.graphics.BitmapFactory
import android.media.ThumbnailUtils
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.destinyed.statussaverpro.Adapters.StatusAdapter
import com.destinyed.statussaverpro.Constants.ConstantsVariables
import com.destinyed.statussaverpro.Models.Models

import com.destinyed.statussaverpro.R
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.lang.Exception
import java.nio.channels.FileChannel
import kotlin.collections.ArrayList

/**
 * A simple [Fragment] subclass.
 */
class Images : Fragment() {

    private lateinit var recyclerView: RecyclerView

    var statusArr = ArrayList<Models>()

    private lateinit var errorStatusImg : LinearLayout

    private lateinit var imageAdapter : StatusAdapter

    private lateinit var extensionImg : String

    private lateinit var openWhatApp : TextView

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



        if (!ConstantsVariables.whatExits.exists()){
            errorStatusImg.visibility = View.VISIBLE
        }
        else{
            getStatus()
        }


        openWhatApp.setOnClickListener {
            showWhatsApp()
        }
    }

    private fun showWhatsApp() {
        try {
            var intent = context!!.packageManager.getLaunchIntentForPackage("com.whatsapp")
            startActivity(intent)
        }catch (e:Exception){
            Toast.makeText(context, "WhatsApp Can't Be Found On Your Device", Toast.LENGTH_LONG).show()
        }
    }

    private fun getStatus() {

        if (ConstantsVariables.whatsApp_Path_Dir.exists()){

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

                try {
                    for (img in files){
                        var path = img.absolutePath
                        var ext = path.takeLast(4)
                        if (ext == ConstantsVariables.JPG){

                            imageAdapter = StatusAdapter(context!!, statusArr, Images())
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
            activity!!.sendBroadcast(intent)

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
