package com.status.statussaverpro.Fragments


import android.media.ThumbnailUtils
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.status.statussaverpro.Adapters.Video_Saved_Adapter
import com.status.statussaverpro.Constants.ConstantsVariables
import com.status.statussaverpro.Models.Models

import com.status.statussaverpro.R

/**
 * A simple [Fragment] subclass.
 */
class Saved_Videos : Fragment() {

    private lateinit var recyclerView : RecyclerView

    private var videoArr = ArrayList<Models>()

    private lateinit var adapter : Video_Saved_Adapter

    private lateinit var errorStatus : LinearLayout
    private lateinit var extensionSaved : String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_saved__videos, container, false)
        recyclerView = root.findViewById(R.id.recyclerViewSavedVideo)

        extensionSaved = String()
        errorStatus = root.findViewById(R.id.showErrorSavedVid)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Write all code to be executed here

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(context, 2)

        showVideo()
    }

    private fun showVideo() {
        var videoDir = ConstantsVariables.AppDirVideo
        var videoFiles = videoDir.listFiles()

        try {
            for (files in videoFiles){
                /**
                 * get name, path, file and set video Thumbnail
                 */
                var title = files.name
                var path = files.absolutePath
                var file = files.absoluteFile
                extensionSaved = path.takeLast(4)

                var videoThumbnail = ThumbnailUtils.createVideoThumbnail(path, MediaStore.Images.Thumbnails.MINI_KIND)

                if (videoFiles.isNotEmpty()){
                    videoArr.add(Models(file, title, path, videoThumbnail!!))
                }
            }
        }catch (e:Exception){
            errorStatus.visibility = View.VISIBLE
        }

        if (extensionSaved != ".mp4" || !ConstantsVariables.AppDirVideo.exists()){
            errorStatus.visibility = View.VISIBLE
        }else{
            errorStatus.visibility = View.GONE
            adapter = Video_Saved_Adapter(context!!, videoArr)
            recyclerView.adapter = adapter
            adapter.notifyDataSetChanged()
        }
    }


}
//done