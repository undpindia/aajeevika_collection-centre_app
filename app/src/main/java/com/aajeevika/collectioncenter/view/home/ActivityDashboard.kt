package com.aajeevika.collectioncenter.view.home

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.aajeevika.collectioncenter.BuildConfig
import com.aajeevika.collectioncenter.R
import com.aajeevika.collectioncenter.baseclasses.BaseActivity
import com.aajeevika.collectioncenter.databinding.ActivityDashboardBinding
import com.aajeevika.collectioncenter.utility.UtilityActions
import com.aajeevika.collectioncenter.utility.app_enum.LanguageType
import com.aajeevika.collectioncenter.view.auth.ActivityLogin
import com.aajeevika.collectioncenter.view.dialog.AlertDialog
import com.aajeevika.collectioncenter.view.grievance.ActivityGrievance
import com.aajeevika.collectioncenter.view.home.adapter.HomeViewPagerAdapter
import com.aajeevika.collectioncenter.view.other.ActivityAboutUs
import com.google.android.material.tabs.TabLayoutMediator

class ActivityDashboard : BaseActivity<ActivityDashboardBinding>(R.layout.activity_dashboard) {

    private val homeViewPagerAdapter by lazy { HomeViewPagerAdapter(supportFragmentManager, lifecycle) }
    private val callRequestCallback = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        if(it == true) {
            val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:${BaseUrls.contactUsNumber}"))
            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding.run {
            version = BuildConfig.VERSION_NAME

            viewPager.adapter = homeViewPagerAdapter
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = when(position) {
                    0 -> getString(R.string.pending)
                    1 -> getString(R.string.received)
                    else -> getString(R.string.delivered)
                }
            }.attach()

            userName = preferencesHelper.userName
            userEmail = preferencesHelper.userEmail
            userProfileImage = preferencesHelper.profileImage
            if(preferencesHelper.appLanguage == LanguageType.ENGLISH.code) languageSwitch.isChecked = false
            else if(preferencesHelper.appLanguage == LanguageType.HINDI.code) languageSwitch.isChecked = true
        }
    }

    override fun initListener() {
        viewDataBinding.run {
            menuBtn.setOnClickListener {
                drawerLayout.openDrawer(GravityCompat.START)
            }

            logoutBtn.setOnClickListener {
                AlertDialog(
                    context = this@ActivityDashboard,
                    message = getString(R.string.logout_confirmation_message),
                    positive = getString(R.string.cancel),
                    negative = getString(R.string.yes_logout),
                    negativeClick = {
                        preferencesHelper.clear()
                        UtilityActions.updateFCM(preferencesHelper)

                        val intent = Intent(this@ActivityDashboard, ActivityLogin::class.java)
                        startActivity(intent)
                        finishAffinity()
                    }
                ).show()
            }

            languageSwitch.setOnCheckedChangeListener { _, isChecked ->
                if(isChecked && preferencesHelper.appLanguage != LanguageType.HINDI.code) {
                    preferencesHelper.appLanguage = LanguageType.HINDI.code

                    startActivity(intent)
                    finish()
                    overridePendingTransition(0, 0)
                } else if(!isChecked && preferencesHelper.appLanguage != LanguageType.ENGLISH.code) {
                    preferencesHelper.appLanguage = LanguageType.ENGLISH.code

                    startActivity(intent)
                    finish()
                    overridePendingTransition(0, 0)
                }
            }

            supportBtn.setOnClickListener {
                callRequestCallback.launch(Manifest.permission.CALL_PHONE)
            }

            grievanveBtn.setOnClickListener {
                viewDataBinding.drawerLayout.closeDrawer(GravityCompat.START)
                val intent = Intent(this@ActivityDashboard, ActivityGrievance::class.java)
                startActivity(intent)
            }

            aboutUsBtn.setOnClickListener {
                viewDataBinding.drawerLayout.closeDrawer(GravityCompat.START)
                val intent = Intent(this@ActivityDashboard, ActivityAboutUs::class.java)
                startActivity(intent)
            }

            drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {
                override fun onDrawerSlide(drawerView: View, slideOffset: Float) {}
                override fun onDrawerStateChanged(newState: Int) {}
                override fun onDrawerClosed(drawerView: View) {}

                override fun onDrawerOpened(drawerView: View) {
                    UtilityActions.closeKeyboard(this@ActivityDashboard, viewDataBinding.root)
                }
            })
        }
    }
}