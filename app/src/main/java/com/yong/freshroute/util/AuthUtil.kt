package com.yong.freshroute.util

import com.google.firebase.auth.FirebaseAuth

object AuthUtil {
    private lateinit var authApp: FirebaseAuth

    private fun initAuth() {
        authApp = FirebaseAuth.getInstance()
    }

    fun getUserInfo(): UserInfo? {
        val curUser = authApp.currentUser ?: return null
        return UserInfo(curUser.displayName!!, curUser.email!!, curUser.uid)
    }

    fun isLoggedIn(): Boolean {
        initAuth()
        return authApp.currentUser != null
    }

    fun isNewUser(userID: String): Boolean {
        return false
    }
}