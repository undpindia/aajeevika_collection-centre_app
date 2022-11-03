package com.aajeevika.collectioncenter.view.auth

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import com.aajeevika.collectioncenter.R
import com.aajeevika.collectioncenter.baseclasses.BaseActivityVM
import com.aajeevika.collectioncenter.databinding.ActivitySplashScreenBinding
import com.aajeevika.collectioncenter.view.auth.viewmodel.SplashScreenViewModel
import com.aajeevika.collectioncenter.view.home.ActivityDashboard

class ActivitySplashScreen : BaseActivityVM<ActivitySplashScreenBinding, SplashScreenViewModel>(
    R.layout.activity_splash_screen,
    SplashScreenViewModel::class
) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        if(preferencesHelper.authToken.isNotEmpty()) {
            viewModel.requestUserProfile()
        } else if(!preferencesHelper.languageSelected) {
            Handler(Looper.getMainLooper()).postDelayed({
                val intent = Intent(this@ActivitySplashScreen, ActivitySelectLanguage::class.java)
                startActivity(intent)
                finish()
            }, 1000)
        } else {
            Handler(Looper.getMainLooper()).postDelayed({
                val intent = Intent(this@ActivitySplashScreen, ActivityLogin::class.java)
                startActivity(intent)
                finish()
            }, 1000)
        }
    }

    override fun observeData() {
        super.observeData()

        viewModel.userLiveData.observe(this, {
            it.user?.run {
                preferencesHelper.uid = id ?: -1
                preferencesHelper.roleId = role_id ?: -1
                preferencesHelper.collectionCenterId = collection_center_id ?: -1
                preferencesHelper.userName = name ?: ""
                preferencesHelper.userEmail = email ?: ""
                preferencesHelper.profileImage = profileImage ?: ""
            }

            val intent = Intent(this, ActivityDashboard::class.java)
            startActivity(intent)
            finish()
        })
    }
}