package com.aajeevika.collectioncenter.utility

import android.content.SharedPreferences
import com.aajeevika.collectioncenter.utility.app_enum.LanguageType

class AppPreferencesHelper(private val mPrefs: SharedPreferences) {
    var uid: Int
        get() = mPrefs.getInt("uid", -1)
        set(value) = mPrefs.edit().putInt("uid", value).apply()

    var collectionCenterId: Int
        get() = mPrefs.getInt("collectionCenterId", -1)
        set(value) = mPrefs.edit().putInt("collectionCenterId", value).apply()

    var roleId: Int
        get() = mPrefs.getInt("roleId", -1)
        set(value) = mPrefs.edit().putInt("roleId", value).apply()

    var userName: String
        get() = mPrefs.getString("userName", "").toString()
        set(value) = mPrefs.edit().putString("userName", value).apply()

    var userEmail: String
        get() = mPrefs.getString("userEmail", "").toString()
        set(value) = mPrefs.edit().putString("userEmail", value).apply()

    var profileImage: String
        get() = mPrefs.getString("profileImage", "").toString()
        set(value) = mPrefs.edit().putString("profileImage", value).apply()

    var authToken: String
        get() = mPrefs.getString("authToken", "").toString()
        set(value) = mPrefs.edit().putString("authToken", value).apply()

    var fcmToken: String
        get() = mPrefs.getString("fcmToken", "").toString()
        set(value) = mPrefs.edit().putString("fcmToken", value).apply()

    var languageSelected: Boolean
        get() = mPrefs.getBoolean("languageSelected", false)
        set(value) = mPrefs.edit().putBoolean("languageSelected", value).apply()

    var appLanguage: String
        get() = mPrefs.getString("appLanguage", LanguageType.ENGLISH.code).toString()
        set(value) = mPrefs.edit().putString("appLanguage", value).apply()

    fun clear() {
        val token = fcmToken
        val selectedLanguage = appLanguage

        mPrefs.edit().clear().apply()

        appLanguage = selectedLanguage
        fcmToken = token
    }
}