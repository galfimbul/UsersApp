package ru.aevshvetsov.usersapp.models

import com.google.gson.annotations.SerializedName

/**
 * Created by Alexander Shvetsov on 22.09.2020
 */
data class NetworkResponse(
    val page: Int,
    @SerializedName("per_page")
    val perPage: Int,
    val total: Int,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("data")
    val users: List<User>
)