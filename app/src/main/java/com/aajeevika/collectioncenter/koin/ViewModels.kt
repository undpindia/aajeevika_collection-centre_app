package com.aajeevika.collectioncenter.koin

import com.aajeevika.collectioncenter.view.auth.viewmodel.*
import com.aajeevika.collectioncenter.view.grievance.viewmodel.CreateGrievanceViewModel
import com.aajeevika.collectioncenter.view.grievance.viewmodel.GrievanceChatViewModel
import com.aajeevika.collectioncenter.view.grievance.viewmodel.GrievanceViewModel
import com.aajeevika.collectioncenter.view.home.viewmodel.OrderViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val myViewModel = module {
    viewModel { SplashScreenViewModel() }
    viewModel { LoginViewModel() }
    viewModel { ForgotPasswordViewModel() }
    viewModel { OtpVerificationViewModel() }
    viewModel { ResetPasswordViewModel() }
    viewModel { OrderViewModel() }
    viewModel { CreateGrievanceViewModel() }
    viewModel { GrievanceChatViewModel() }
    viewModel { GrievanceViewModel() }
}