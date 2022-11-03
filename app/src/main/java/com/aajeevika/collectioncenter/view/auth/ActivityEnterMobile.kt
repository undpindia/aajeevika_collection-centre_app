package com.aajeevika.collectioncenter.view.auth

import android.content.Intent
import com.aajeevika.collectioncenter.R
import com.aajeevika.collectioncenter.baseclasses.BaseActivityVM
import com.aajeevika.collectioncenter.databinding.ActivityEnterMobileBinding
import com.aajeevika.collectioncenter.utility.Constant
import com.aajeevika.collectioncenter.utility.UtilityActions
import com.aajeevika.collectioncenter.utility.UtilityActions.debugOtp
import com.aajeevika.collectioncenter.utility.UtilityActions.showMessage
import com.aajeevika.collectioncenter.view.auth.viewmodel.ForgotPasswordViewModel

class ActivityEnterMobile : BaseActivityVM<ActivityEnterMobileBinding, ForgotPasswordViewModel>(
    R.layout.activity_enter_mobile,
    ForgotPasswordViewModel::class
) {
    var mobileNumber = ""

    override fun observeData() {
        super.observeData()

        viewModel.otpLiveData.observe(this, {
            debugOtp(it.otp)

            val intent = Intent(this@ActivityEnterMobile, ActivityOtpVerification::class.java)
            intent.putExtra(Constant.MOBILE_NUMBER, mobileNumber)
            startActivity(intent)
        })
    }

    override fun initListener() {
        viewDataBinding.run {
            toolbar.backBtn.setOnClickListener {
                onBackPressed()
            }

            verifyBtn.setOnClickListener {
                mobileNumber = inputMobileNumber.text.toString()

                validateFormData(mobileNumber)?.let { error ->
                    root.showMessage(error)
                } ?: run {
                    viewModel.requestForgotPassword(mobileNumber)
                }
            }
        }
    }

    private fun validateFormData(mobileNumber: String): String? {
        return if (!UtilityActions.isValidMobileNumber(mobileNumber)) getString(R.string.enter_valid_mobile_number)
        else null
    }
}