package com.aajeevika.collectioncenter

import android.app.Application
import android.content.Context
import com.aajeevika.collectioncenter.koin.appModule
import com.aajeevika.collectioncenter.koin.myViewModel
import com.aajeevika.collectioncenter.koin.networkModule
import com.aajeevika.collectioncenter.utility.AppPreferencesHelper
import com.aajeevika.collectioncenter.utility.LocaleHelper
import com.google.firebase.messaging.FirebaseMessaging
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApp : Application() {

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(
            LocaleHelper.updateLanguage(
                newBase,
                AppPreferencesHelper(newBase.getSharedPreferences(
                    "${BuildConfig.APPLICATION_ID}_app",
                    MODE_PRIVATE
                )).appLanguage
            )
        )
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MyApp)
            modules(listOf(appModule, networkModule, myViewModel))
        }

        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            if(it.isSuccessful) {
                val preferences: AppPreferencesHelper = get()
                preferences.fcmToken = it.result ?: ""
            }
        }
    }
}