package com.yong.freshroute.util

object AuthUtil {
    fun getUserInfo(userID: String): UserInfo {
        return UserInfo("NAME", "EMAIL", "UID", "TOKEN")
    }
    fun isNewUser(userID: String): Boolean {
        return false
    }

    fun isTokenAvail(userToken: String): Boolean {
        return true
    }

    fun tryFirebaseLogin(): UserInfo {
        return UserInfo("NAME", "EMAIL", "UID", "TOKEN")
    }

    fun tryLogin(userToken: String?): UserInfo {
        return UserInfo("NAME", "EMAIL", "UID", "TOKEN")
    }
}