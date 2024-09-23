package com.example.newsshorts.core.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import java.io.File
import java.io.FileOutputStream

object Helper {


    suspend fun loadImageFromUrl(imageUrl: String, context: Context): Bitmap? {
        val imageLoader = ImageLoader(context = context)
        val request = ImageRequest.Builder(context)
            .data(imageUrl)
            .allowHardware(false)
            .build()
        val result = (imageLoader.execute(request) as SuccessResult).drawable
        return (result as BitmapDrawable).bitmap
    }

    fun saveImageToCache(bitmap: Bitmap, context: Context, fileName: String): File? {
        val cachePath = File(context.externalCacheDir, "shared_images")
        cachePath.mkdirs()
        val file = File(cachePath, "${fileName}.png")
        try {
            val outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.flush()
            outputStream.close()
            return file

        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

}