package com.destinyed.statussaverpro.Fragments


import android.content.Intent
import android.media.ThumbnailUtils
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.destinyed.statussaverpro.Adapters.Video_Statust_Adapter
import com.destinyed.statussaverpro.Constants.ConstantsVariables
import com.destinyed.statussaverpro.Models.VideoModel
import com.destinyed.statussaverpro.R
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.lang.Exception
import java.nio.channels.FileChannel

/**
 * A simple [Fragment] subclass.
 */
class Videos : Fragment() {

    private lateinit var recyclerView: RecyclerView

    var statusArr = ArrayList<VideoModel>()
    private lateinit var videoAdapter : Video_Statust_Adapter

    private lateinit var errorStatusVid : LinearLayout

    private lateinit var extensionVid : String
    private lateinit var showWhatApp : TextView

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



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(context, 2)

        if (!ConstantsVariables.whatExits.exists() && !ConstantsVariables.whaSecondtExits.exists()){
            errorStatusVid.visibility = View.VISIBLE
        }else{
            getStatus()
        }



        showWhatApp.setOnClickListener {
            openWhatsApp()
        }
    }

    private fun openWhatsApp() {
        try {
            val intent = context!!.packageManager.getLaunchIntentForPackage("com.whatsapp")
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
                           videoAdapter = Video_Statust_Adapter(context!!, statusArr, Videos())
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

            if(ConstantsVariables.whatsApp_Path_second.exists()){
                var files = ConstantsVariables.whatsApp_Path_second.listFiles()

                try {
                    for (app in files){
                        var path = app.absolutePath
                        var ext = path.takeLast(4)
                        if (ext == ConstantsVariables.MP4){
                            videoAdapter = Video_Statust_Adapter(context!!, statusArr, Videos())
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

    fun downloadVideo(statusDownload: VideoModel) {
        var file = ConstantsVariables.AppDirVideo
        if (!file.exists()){
            file.mkdirs()
        }
        var destinationfile = File(file.toString(), statusDownload.title)

        if (!destinationfile.exists()){
            destinationfile.delete()
        }

        try {
            copyFile(statusDownload.file, destinationfile)

            //refresh gallery
            var intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
            intent.data = Uri.fromFile(destinationfile)
            activity!!.sendBroadcast(intent)

        }catch (e: Exception){
            e.printStackTrace()
        }
    }


    private fun copyFile(file: File, destinationfile: File) {

        if (!destinationfile.parentFile.exists()){
            destinationfile.parentFile.mkdirs()
        }

        if (!destinationfile.exists()){
            destinationfile.createNewFile()
        }

        var source : FileChannel? = null
        var destination : FileChannel? = null

        source = FileInputStream(file).channel
        destination = FileOutputStream(destinationfile).channel

        destination.transferFrom(source, 0, source.size())

        source.close()
        destination.close()


    }

}


