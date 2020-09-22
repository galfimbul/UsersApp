package ru.aevshvetsov.usersapp.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import ru.aevshvetsov.usersapp.R

/**
 * Created by Alexander Shvetsov on 22.09.2020
 */
fun ImageView.loadImage(avatarUrl: String) {
    val requestOptions = RequestOptions().placeholder(R.drawable.ic_placeholder)
    Glide.with(this)
        .setDefaultRequestOptions(requestOptions)
        .load(avatarUrl)
        .into(this)

}