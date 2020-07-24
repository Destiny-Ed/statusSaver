package com.destinyed.statussaverpro.Adapters

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.destinyed.statussaverpro.Models.Models
import com.destinyed.statussaverpro.R
import com.destinyed.statussaverpro.showImageAndVideo.ImageSavedFullScreen

class ImageSavedAdapter(var ctx : Context, var imageArr : ArrayList<Models>) : RecyclerView.Adapter<ImageSavedAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ImageSavedAdapter.ViewHolder {
        //inflate layout
        var rootLayout = LayoutInflater.from(parent.context).inflate(R.layout.saved_image_layout, parent, false)

        return ViewHolder(rootLayout)
    }

    override fun getItemCount(): Int {
        //return imageArr count
        return imageArr.size
    }

    override fun onBindViewHolder(holder: ImageSavedAdapter.ViewHolder, position: Int) {
        //bindItems to Holder
        holder.bindItems(imageArr[position])
    }


    //create an innerclass Viewholder
    inner class ViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){

        fun bindItems(imageM : Models) {
            //get all variables from the inflated layout
            var imageThumbnailSaved = itemView.findViewById<ImageView>(R.id.imageThumbnailSaved)

            //set the bitmap image
            imageThumbnailSaved.setImageBitmap(imageM.image)


            //set onClick Listener to image
            imageThumbnailSaved.setOnClickListener {
                //set image uri to ImageFullScreen
                var dialog = AlertDialog.Builder(ctx)
                dialog.setTitle("Message")
                dialog.setNeutralButton("Delete Forever") {
                    _, _ ->
                    imageM.file.delete()
                }
                dialog.setPositiveButton("View Image"){
                    _, _ ->

                    var intent = Intent(ctx, ImageSavedFullScreen::class.java)
                    intent.putExtra("imageUri", imageM.path)
                    intent.putExtra("title", imageM.title)
                    ctx.startActivity(intent)
                }
                dialog.create().show()



            }


        }
    }
}

//done