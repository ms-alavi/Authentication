package com.authentication.model.repository
import com.authentication.model.local.Credentials
import com.authentication.model.local.User
import com.authentication.model.remote.ApiService
import com.google.gson.JsonObject
import io.reactivex.Single
import javax.inject.Inject

class RemoteRepository@Inject constructor(
    private val apiService: ApiService,
) {

     fun login(username: String, password: String): Single<Credentials> {
        return apiService.login(JsonObject().apply {
            addProperty("username", username)
            addProperty("password", password)
        })
    }

    fun getUser(): Single<User> = apiService.getUser()


}
