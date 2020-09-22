package ru.aevshvetsov.usersapp.network

import retrofit2.http.GET
import retrofit2.http.Query
import ru.aevshvetsov.usersapp.models.NetworkResponse

/**
 * Created by Alexander Shvetsov on 22.09.2020
 */
interface UsersListApi {
    @GET("users")
    suspend fun getUsersList(
        @Query("page") page: String
    ): NetworkResponse
}