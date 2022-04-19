package com.authentication.model.repository

import android.content.SharedPreferences
import com.authentication.model.local.Credentials
import com.authentication.model.local.User
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class Repository @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val sharedPreferences: SharedPreferences
) {


    fun login(username: String, password: String): Completable {
        return remoteRepository.login(username, password).doOnSuccess {
            onSuccessfulLogin(it)
        }.ignoreElement()
    }

    fun getUser(): Single<User> = remoteRepository.getUser()

    private fun onSuccessfulLogin(credentials: Credentials) {
        TokenContainer.update(credentials.token, credentials.refreshToken)
        sharedPreferences.edit().apply {
            putString("access_token", credentials.token)
            putString("refresh_token", credentials.refreshToken)
        }.apply()

    }
}
