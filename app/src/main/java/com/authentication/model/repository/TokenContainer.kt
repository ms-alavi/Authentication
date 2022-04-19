package com.authentication.model.repository
//Token store in this class
object TokenContainer {
    var token: String? = null
        private set
    var refreshToken: String? = null
        private set

    fun update(token: String?, refreshToken: String?) {
        this.token = token
        this.refreshToken = refreshToken
    }
}