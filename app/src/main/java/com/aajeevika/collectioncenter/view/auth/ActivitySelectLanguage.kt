package com.aajeevika.collectioncenter.view.auth

import android.content.Intent
import android.os.Bundle
import com.aajeevika.collectioncenter.R
import com.aajeevika.collectioncenter.baseclasses.BaseActivity
import com.aajeevika.collectioncenter.databinding.ActivitySelectLanguageBinding
import com.aajeevika.collectioncenter.utility.app_enum.LanguageType

class ActivitySelectLanguage : BaseActivity<ActivitySelectLanguageBinding>(R.layout.activity_select_language) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding.selectedLanguage = when(preferencesHelper.appLanguage) {
            LanguageType.ENGLISH.code -> LanguageType.ENGLISH
            else -> LanguageType.HINDI
        }
    }

    override fun initListener() {
        viewDataBinding.run {
            proceedBtn.setOnClickListener {
                preferencesHelper.languageSelected = true

                val intent = Intent(this@ActivitySelectLanguage, ActivityLogin::class.java)
                startActivity(intent)
                finish()
            }

            englishBtn.setOnClickListener {
                handleSelectedLanguage(LanguageType.ENGLISH)
            }

            hindiBtn.setOnClickListener {
                handleSelectedLanguage(LanguageType.HINDI)
            }
        }
    }

    private fun handleSelectedLanguage(languageType: LanguageType) {
        preferencesHelper.appLanguage = languageType.code

        viewDataBinding.selectedLanguage = languageType
        viewDataBinding.executePendingBindings()

        startActivity(intent)
        finish()
        overridePendingTransition(0, 0)
    }
}