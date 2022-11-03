package com.aajeevika.collectioncenter.koin

import android.content.Context
import com.aajeevika.collectioncenter.BuildConfig
import com.aajeevika.collectioncenter.utility.AppPreferencesHelper
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    single {
        androidContext().getSharedPreferences("${BuildConfig.APPLICATION_ID}_app", Context.MODE_PRIVATE)
    }

    single {
        AppPreferencesHelper(get())
    }
}