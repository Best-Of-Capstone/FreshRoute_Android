package com.yong.freshroute.util

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.google.android.gms.auth.api.identity.BeginSignInRequest
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
        initAuth()
        return authApp.currentUser != null
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
        BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setFilterByAuthorizedAccounts(true)
                    .setSupported(true)
                    .build()
            )
            .build()
        return UserInfo("NAME", "EMAIL", "UID", "TOKEN")
    }
}