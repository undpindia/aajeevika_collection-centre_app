package com.aajeevika.collectioncenter.view.auth

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.core.widget.doOnTextChanged
import com.aajeevika.collectioncenter.R
import com.aajeevika.collectioncenter.baseclasses.BaseActivityVM
import com.aajeevika.collectioncenter.databinding.ActivityOtpVerificationBinding
import com.aajeevika.collectioncenter.utility.*
import com.aajeevika.collectioncenter.utility.UtilityActions.debugOtp
import com.aajeevika.collectioncenter.utility.UtilityActions.showMessage
import com.aajeevika.collectioncenter.view.auth.viewmodel.OtpVerificationViewModel

class ActivityOtpVerification : BaseActivityVM<ActivityOtpVerificationBinding, OtpVerificationViewModel>(
    R.layout.activity_otp_verification,
    OtpVerificationViewModel::class
) {
    private lateinit var countDownTimer: CountDownTimer
    private val mobileNumber: String by lazy { intent.getStringExtra(Constant.MOBILE_NUMBER) ?: "" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding.message = String.format(getString(R.string.enter_4_digit_otp_sent_to_s), mobileNumber)

        initiateTimer()
    }

    override fun onResume() {
        super.onResume()
        UtilityActions.openKeyboard(this, viewDataBinding.inputOtpView)
    }

    override fun onDestroy() {
        if (::countDownTimer.isInitialized) countDownTimer.cancel()
        super.onDestroy()
    }

    override fun observeData() {
        super.observeData()
        viewModel.otpLiveData.observe(this, {
            debugOtp(it.otp)
        })

        viewModel.userProfileLiveData.observe(this, {
            val intent = Intent(this, ActivityResetPassword::class.java)
            intent.putExtra(Constant.OTP, viewDataBinding.inputOtpView.text.toString().toInt())
            intent.putExtra(Constant.MOBILE_NUMBER, mobileNumber)
            startActivity(intent)
            finish()
        })
    }

    override fun initListener() {
        viewDataBinding.run {
            toolbar.backBtn.setOnClickListener {
                onBackPressed()
            }

            resendTxt.setOnClickListener {
                viewDataBinding.inputOtpView.text = null
                viewModel.requestOtpResend(mobileNumber)
                initiateTimer()
            }

            verifyBtn.setOnClickListener {
                val pin = viewDataBinding.inputOtpView.text.toString().trim()

                if (pin.length == 4)
                    viewModel.requestOtpVerification(mobileNumber, pin)
                else
                    viewDataBinding.root.showMessage(getString(R.string.enter_valid_otp))
            }

            inputOtpView.doOnTextChanged { pin, _, _, _ ->
                if ((pin?.length ?: 0) == 4) {
                    UtilityActions.closeKeyboard(this@ActivityOtpVerification, inputOtpView)
                }
            }
        }
    }

    private fun initiateTimer() {
        if (::countDownTimer.isInitialized) countDownTimer.cancel()
        countDownTimer = object : CountDownTimer(30000, 1000) {
            override fun onFinish() = handleCountdown(-1)
            override fun onTick(millisUntilFinished: Long) = handleCountdown((millisUntilFinished / 1000).toInt() + 1)
        }.start()
    }

    private fun handleCountdown(countdown: Int) {
        if (countdown == -1) {
            viewDataBinding.timerTxt.visibility = View.INVISIBLE
            viewDataBinding.resendTxt.visibility = View.VISIBLE
        } else {
            viewDataBinding.timerTxt.visibility = View.VISIBLE
            viewDataBinding.resendTxt.visibility = View.INVISIBLE
            viewDataBinding.timerTxt.text = String.format(getString(R.string.remaining_d_sec), countdown)
        }
    }
}

