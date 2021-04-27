package com.example.redditandroid.api

import android.content.Context
import android.database.Cursor
import android.net.Uri

import android.provider.MediaStore
import android.util.Log
import com.ipaulpro.afilechooser.utils.FileUtils
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.net.URI


class FileUpload {

    fun uploadFile(fileUri: Uri, mContext:Context?):MultipartBody.Part{

        val file:File = File(fileUri.path)
        Log.d("Authentication","File object created")

//        val requestFile = RequestBody.create(
//            MediaType.parse(mContext?.contentResolver?.getType(fileUri)),
//            file
//        )
        val requestFile = RequestBody.create(
            MediaType.parse("image/jpg"),file)
        Log.d("Authentication","request file created")

        val body:MultipartBody.Part = MultipartBody.Part.createFormData("display_picture", file.name, requestFile)

        Log.d("Authentication","Multipart body created")
        return body

    }


}