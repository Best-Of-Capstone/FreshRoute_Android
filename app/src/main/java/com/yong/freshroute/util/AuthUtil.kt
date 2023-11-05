package com.yong.freshroute.util

import com.google.firebase.auth.FirebaseAuth

object AuthUtil {
    private lateinit var authApp: FirebaseAuth

    private fun initAuth() {
        authApp = FirebaseAuth.getInstance()
    }

    fun getUserInfo(userID: String): UserInfo {
        return UserInfo("NAME", "EMAIL", "UID", "TOKEN")
    }

    fun isLoggedIn(): Boolean {
        return true
    }

    fun isNewUser(userID: String): Boolean {
        return false
    }

    fun isTokenAvail(): Boolean {
        return true
    }

    fun tryFirebaseLogin(): UserInfo {
        return UserInfo("NAME", "EMAIL", "UID", "TOKEN")
    }

    fun tryLogin(): UserInfo {
        val curUser = authApp.currentUser
        return UserInfo("NAME", "EMAIL", "UID", "TOKEN")
    }
}