package com.rs.codingexercisegyg.utils

import android.content.Context
import android.net.ConnectivityManager
import android.widget.ImageView
import com.squareup.picasso.Picasso

fun String.loadImage(imageView: ImageView) {
    this.let {
        Picasso.get().load(it).fit().centerInside()
            .placeholder(android.R.drawable.ic_menu_gallery).into(imageView)
    }
}

fun Context.verifyAvailableNetwork(): Boolean {
    val connectivityManager =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo = connectivityManager.activeNetworkInfo
    return networkInfo != null && networkInfo.isConnected
}