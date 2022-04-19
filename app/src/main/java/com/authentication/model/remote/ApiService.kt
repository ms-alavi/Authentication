package com.authentication.model.remote

import com.authentication.model.local.Credentials
import com.authentication.model.local.User
import com.google.gson.JsonObject
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("credentials")
    fun login(@Body jsonObject: JsonObject): Single<Credentials>

    @GET("user")
    fun getUser(): Single<User>


}