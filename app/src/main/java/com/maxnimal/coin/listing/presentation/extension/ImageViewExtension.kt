package com.maxnimal.coin.listing.presentation.extension

import android.widget.ImageView
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.CachePolicy
import coil.request.ImageRequest

fun ImageView.loadImageFromUrl(url: String) {
    val imageLoader = ImageLoader.Builder(this.context)
//        .memoryCachePolicy(CachePolicy.ENABLED)
//        .diskCachePolicy(CachePolicy.ENABLED)
        .components {
            add(SvgDecoder.Factory())
        }
        .build()
    val request = ImageRequest.Builder(this.context)
//        .memoryCachePolicy(CachePolicy.ENABLED)
//        .diskCachePolicy(CachePolicy.ENABLED)
        .data(url)
        .target { drawable ->
           this.setImageDrawable(drawable)
        }
        .build()
    imageLoader.enqueue(request)
}