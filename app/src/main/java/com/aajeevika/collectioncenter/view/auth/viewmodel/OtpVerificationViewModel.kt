package com.aajeevika.collectioncenter.view.auth.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aajeevika.collectioncenter.baseclasses.BaseViewModel
import com.aajeevika.collectioncenter.model.data_model.OtpModel
import com.aajeevika.collectioncenter.model.data_model.UserProfileModel
import com.aajeevika.collectioncenter.utility.app_enum.ErrorType
import com.aajeevika.collectioncenter.utility.app_enum.ProgressAction

class OtpVerificationViewModel : BaseViewModel() {
    private val _userProfileLiveData = MutableLiveData<UserProfileModel>()
    val userProfileLiveData: LiveData<UserProfileModel> = _userProfileLiveData

    private val _otpLiveData = MutableLiveData<OtpModel>()
    val otpLiveData: LiveData<OtpModel> = _otpLiveData

    fun requestOtpVerification(mobileNo: String, otp: String) {
        val map = HashMap<String, Any>()
        map["mobile"] = mobileNo
        map["otp"] = otp

        requestData(
            { apiService.verifyOtp(map) },
            { it.data.run { _userProfileLiveData.postValue(this) } },
            progressAction = ProgressAction.PROGRESS_DIALOG,
            errorType = ErrorType.DIALOG,
        )
    }

    fun requestOtpResend(mobileNo: String, isChangeMobile: Boolean = false) {
        val map = HashMap<String, Any>()

        map["mobile"] = mobileNo
        if (isChangeMobile) map["type"] = "updatemobile"

        requestData(
            { apiService.resendOtp(map) },
            { it.data?.run { _otpLiveData.postValue(this) } },
            progressAction = ProgressAction.PROGRESS_DIALOG,
            errorType = ErrorType.DIALOG,
        )
    }
}