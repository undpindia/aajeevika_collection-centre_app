package com.aajeevika.collectioncenter.view.auth

import android.content.Intent
import android.util.Patterns
import com.aajeevika.collectioncenter.R
import com.aajeevika.collectioncenter.baseclasses.BaseActivityVM
import com.aajeevika.collectioncenter.databinding.ActivityLoginBinding
import com.aajeevika.collectioncenter.utility.UtilityActions.showMessage
import com.aajeevika.collectioncenter.view.auth.viewmodel.LoginViewModel
import com.aajeevika.collectioncenter.view.home.ActivityDashboard

class ActivityLogin : BaseActivityVM<ActivityLoginBinding, LoginViewModel>(
    R.layout.activity_login,
    LoginViewModel::class
) {
    override fun observeData() {
        super.observeData()

        viewModel.userProfileLiveData.observe(this, {
            it.user?.run {
                preferencesHelper.uid = id ?: -1
                preferencesHelper.roleId = role_id ?: -1
                preferencesHelper.authToken = api_token ?: ""
                preferencesHelper.collectionCenterId = collection_center_id ?: -1
                preferencesHelper.userName = name ?: ""
                preferencesHelper.userEmail = email ?: ""
                preferencesHelper.profileImage = profileImage ?: ""
            }

            val intent = Intent(this, ActivityDashboard::class.java)
            startActivity(intent)
            finishAffinity()
        })
    }

    override fun initListener() {
        viewDataBinding.run {
            forgotPasswordBtn.setOnClickListener {
                val intent = Intent(this@ActivityLogin, ActivityEnterMobile::class.java)
                startActivity(intent)
            }

            loginBtn.setOnClickListener {
                val phoneOrEmail = viewDataBinding.inputEmail.text.toString().trim()
                val password = viewDataBinding.inputPassword.text.toString().trim()

                validateData(phoneOrEmail, password)?.let {
                    viewDataBinding.root.showMessage(it)
                } ?: run {
                    viewModel.requestUserLogin(phoneOrEmail, password)
                }
            }
        }
    }

    private fun validateData(phoneOrEmail: String, password: String): String? = run {
        when {
            phoneOrEmail.isEmpty() -> getString(R.string.enter_mobile_or_email)
            !Patterns.PHONE.matcher(phoneOrEmail).matches() && !Patterns.EMAIL_ADDRESS.matcher(phoneOrEmail).matches() -> getString(R.string.invalid_mobile_or_email)
            password.isEmpty() -> getString(R.string.enter_password)
            password.length < 8 -> getString(R.string.password_length_error)
            else -> null
        }
    }
}