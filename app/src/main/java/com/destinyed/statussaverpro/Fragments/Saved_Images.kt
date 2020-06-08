package com.destinyed.statussaverpro.Fragments


import android.graphics.BitmapFactory
import android.media.ThumbnailUtils
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.destinyed.statussaverpro.Adapters.ImageSavedAdapter
import com.destinyed.statussaverpro.Constants.ConstantsVariables
import com.destinyed.statussaverpro.Models.Models

import com.destinyed.statussaverpro.R

/**
 * A simple [Fragment] subclass.
 */
class Saved_Images : Fragment() {

    private lateinit var recyclerView : RecyclerView
    private lateinit var adapter : ImageSavedAdapter

    private var imageArr = ArrayList<Models>()

    private lateinit var errorStatus : LinearLayout

    private lateinit var extension : String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_saved__images, container, false)
        recyclerView = root.findViewById(R.id.recyclerViewSaved)

        extension = String()
        errorStatus = root.findViewById(R.id.showErrorSavedImg)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Write all code to be executed here
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(context, 2)

        showImageStatus()



    }

    private fun showImageStatus() {
        var imageFileDir = ConstantsVariables.AppDirImage
        var listFiles = imageFileDir.listFiles()

        try {
            for (images in listFiles){
                var title = images.name
                var path = images.absolutePath
                var file = images.absoluteFile
                extension = path.takeLast(4)
                var image = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(path), ConstantsVariables.THUMBNAILS,
                    ConstantsVariables.THUMBNAILS)

                if (!listFiles.isEmpty()){
                    imageArr.add(Models(file, title, path, image))
                }
            }
        }catch (e:Exception){
            errorStatus.visibility = View.VISIBLE
        }

        if (!ConstantsVariables.AppDirImage.exists()){
            errorStatus.visibility = View.VISIBLE
        }else{
            errorStatus.visibility = View.GONE
            //set images to imageSavedAdapter
            adapter = ImageSavedAdapter(context!!, imageArr)
            recyclerView.adapter = adapter
            adapter.notifyDataSetChanged()
        }
    }


}
