package com.aajeevika.collectioncenter.model.data_model

import com.aajeevika.collectioncenter.utility.app_enum.ErrorType

data class ErrorDataModel(
    val message: String,
    val errorType: ErrorType,
)