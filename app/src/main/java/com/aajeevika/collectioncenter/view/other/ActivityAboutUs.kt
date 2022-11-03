package com.aajeevika.collectioncenter.view.other

import com.aajeevika.collectioncenter.R
import com.aajeevika.collectioncenter.baseclasses.BaseActivity
import com.aajeevika.collectioncenter.databinding.ActivityAboutUsBinding

class ActivityAboutUs : BaseActivity<ActivityAboutUsBinding>(R.layout.activity_about_us) {
    override fun initListener() {
        viewDataBinding.toolbar.backBtn.setOnClickListener {
            onBackPressed()
        }
    }
}