package com.destinyed.statussaverpro.Constants

import android.os.Environment
import java.io.File

object ConstantsVariables {

    val whatsApp_Path_Dir = File(Environment.getExternalStorageDirectory().toString() + "/WhatsApp/Media/.Statuses")
    val whatsApp_Path_second = File(Environment.getExternalStorageDirectory().toString() + "/Android/media/com.whatsapp/WhatsApp/Media/.Statuses");
    val whatsApp_Path_DirVideo = File(Environment.getExternalStorageDirectory().toString() + "/WhatsApp/Media/.Statuses")

    var MP4 = ".mp4"

    var JPG = ".jpg"

    val whatExits = File(Environment.getExternalStorageDirectory().toString() + "/WhatsApp")
    val whaSecondtExits = File(Environment.getExternalStorageDirectory().toString() + "/Android/media/com.whatsapp/WhatsApp")


    val AppDirImage = File(Environment.getExternalStorageDirectory().toString() + "/StatusSaverProDir/Images")

    val AppDirVideo = File(Environment.getExternalStorageDirectory().toString() + "/StatusSaverProDir/Videos")



    const val THUMBNAILS = 128


}