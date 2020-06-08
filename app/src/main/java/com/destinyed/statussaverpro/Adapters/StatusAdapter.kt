package com.destinyed.statussaverpro.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.destinyed.statussaverpro.Constants.ConstantsVariables
import com.destinyed.statussaverpro.Fragments.Images
import com.destinyed.statussaverpro.MainActivity
import com.destinyed.statussaverpro.Models.Models
import com.destinyed.statussaverpro.R
import com.destinyed.statussaverpro.showImageAndVideo.ImageFullScreen
import java.lang.Exception

class StatusAdapter(var ctx : Context, var statusArr : ArrayList<Models>, var imageFragment : Images) : RecyclerView.Adapter<StatusAdapter.StatusHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatusAdapter.StatusHolder {
        //inflate layout here
        var root =
            LayoutInflater.from(parent.context).inflate(R.layout.status_layout, parent, false)

        return StatusHolder(root)
    }

    override fun getItemCount(): Int {
        return statusArr.size
    }

    override fun onBindViewHolder(holder: StatusAdapter.StatusHolder, position: Int) {
        holder.bindItems(statusArr[position])

    }

    inner class StatusHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(status: Models) {



            //get all items from custom layout
            var imageThumbnail = itemView.findViewById<ImageView>(R.id.imageThumbnail)
            var saveBtn = itemView.findViewById<ImageButton>(R.id.saveBtn)


            imageThumbnail.setImageBitmap(status.image)


            itemView.setOnClickListener {
                    //set image uri to ImageFullScreen
                    var intent = Intent(ctx, ImageFullScreen::class.java)
                    intent.putExtra("imageUri", status.path)
                    intent.putExtra("title", status.title)
                    ctx.startActivity(intent)

            }


            saveBtn.setOnClickListener {
                var statusDownload = statusArr[adapterPosition]

                if (statusDownload != null){
                    try {
                        imageFragment.downloadImage(statusDownload)
                        //show download toast
                        Toast.makeText(ctx, "Downloaded to ${ConstantsVariables.AppDirImage}", Toast.LENGTH_LONG).show()
                        ctx.startActivity(Intent(ctx, MainActivity::class.java))
                    }
                    catch (e:Exception){
                        e.printStackTrace()
                    }
                }

            }


        }

    }
}

//done