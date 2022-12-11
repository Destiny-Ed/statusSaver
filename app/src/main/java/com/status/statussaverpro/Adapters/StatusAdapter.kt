package com.status.statussaverpro.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.status.statussaverpro.Fragments.Images
import com.status.statussaverpro.Models.Models
import com.status.statussaverpro.R
import com.status.statussaverpro.showImageAndVideo.ImageFullScreen
import com.google.android.gms.ads.AdView


class StatusAdapter(var ctx : Context, var statusArr : ArrayList<Any>, var imageFragment : Images) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // A menu item view type.
    private val MENU_ITEM_VIEW_TYPE = 0

    // The banner ad view type.
    private val BANNER_AD_VIEW_TYPE = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : RecyclerView.ViewHolder {
        //inflate layout here

            ///Content

        return when (viewType) {
            MENU_ITEM_VIEW_TYPE -> {
                var root =
                    LayoutInflater.from(parent.context).inflate(R.layout.status_layout, parent, false)

                return StatusHolder(root)
            }
            BANNER_AD_VIEW_TYPE -> {
                var root =
                    LayoutInflater.from(parent.context).inflate(R.layout.item_ads, parent, false)

                return AdViewHolder(root)
            }
            else -> {
                var root =
                    LayoutInflater.from(parent.context).inflate(R.layout.item_ads, parent, false)

                AdViewHolder(root)
            }
        }



    }

    override fun getItemViewType(position: Int): Int {


        return if (position % 8 == 0) BANNER_AD_VIEW_TYPE else MENU_ITEM_VIEW_TYPE

    }

    override fun getItemCount(): Int {
        return statusArr.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val viewType = getItemViewType(position)
        when (viewType) {
            MENU_ITEM_VIEW_TYPE -> {
                val menuItemHolder: StatusHolder = holder as StatusHolder
                val models: Models = statusArr[position] as Models
                holder.bindItems(models)


            }
            BANNER_AD_VIEW_TYPE -> {
                val bannerHolder = holder as AdViewHolder
                val adView : AdView = statusArr[position] as AdView
                val adCardView = bannerHolder.itemView as ViewGroup
                if (adCardView.childCount > 0) {
                    adCardView.removeAllViews()
                }
                if (adView.parent != null) {
                    (adView.parent as ViewGroup).removeView(adView)
                }
                adCardView.addView(adView)
            }
            else -> {
                val bannerHolder = holder as AdViewHolder
                val adView : AdView = statusArr[position] as AdView
                val adCardView = bannerHolder.itemView as ViewGroup
                if (adCardView.childCount > 0) {
                    adCardView.removeAllViews()
                }
                if (adView.parent != null) {
                    (adView.parent as ViewGroup).removeView(adView)
                }
                adCardView.addView(adView)
            }
        }

    }


    inner class AdViewHolder(view: View?) : RecyclerView.ViewHolder(
        view!!
    )

    inner class StatusHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun bindItems(status: Models) {

            //get all items from custom layout
            var imageThumbnail = itemView.findViewById<ImageView>(R.id.imageThumbnail)
//            var saveBtn = itemView.findViewById<ImageButton>(R.id.saveBtn)


            imageThumbnail.setImageBitmap(status.image)


            itemView.setOnClickListener {
                    //set image uri to ImageFullScreen
                    var intent = Intent(ctx, ImageFullScreen::class.java)
                    intent.putExtra("imageUri", status.path)
                    intent.putExtra("title", status.title)
                    ctx.startActivity(intent)

            }


//            saveBtn.setOnClickListener {
//                var statusDownload = statusArr[adapterPosition]
//
//                if (statusDownload != null){
//                    try {
//                        imageFragment.downloadImage(statusDownload)
//                        //show download toast
//                        Toast.makeText(ctx, "Downloaded to ${ConstantsVariables.AppDirImage}", Toast.LENGTH_LONG).show()
//                        ctx.startActivity(Intent(ctx, MainActivity::class.java))
//                    }
//                    catch (e:Exception){
//                        e.printStackTrace()
//                    }
//                }
//
//            }


        }

    }




}

//done