package com.aajeevika.collectioncenter.view.auth.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aajeevika.collectioncenter.baseclasses.BaseViewModel
import com.aajeevika.collectioncenter.model.data_model.OtpModel
import com.aajeevika.collectioncenter.utility.app_enum.ErrorType
import com.aajeevika.collectioncenter.utility.app_enum.ProgressAction

class ForgotPasswordViewModel : BaseViewModel() {
    private val _otpLiveData = MutableLiveData<OtpModel>()
    val otpLiveData: LiveData<OtpModel> = _otpLiveData

    fun requestForgotPassword(mobileNo: String) {
        val map = HashMap<String, Any>()
        map["mobile"] = mobileNo

        requestData(
            { apiService.forgotPassword(map) },
            { it.data?.let { result -> _otpLiveData.postValue(result) } },
            progressAction = ProgressAction.PROGRESS_DIALOG,
            errorType = ErrorType.DIALOG,
        )
    }
}