package com.achaka.imageloadingapp

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class Network {

    fun getImageFromNetwork(passedUrl: String): Bitmap? {
        var url: URL? = null

        if (passedUrl.isNotEmpty()) {
            try {
                url = URL(passedUrl)
            } catch (e: MalformedURLException) {
                return null
            }
        }

        return if (url != null) {
            val connection = url.openConnection() as HttpsURLConnection
            try {
                val byteArr = connection.inputStream.readBytes()
                BitmapFactory.decodeByteArray(byteArr, 0, byteArr.size)
            } catch (e: IOException) {
                null
            } finally {
                connection.disconnect()
            }
        } else null

    }

    companion object NetworkApi {
        fun getNetworkApi(): Network {
            return Network()
        }
    }
}