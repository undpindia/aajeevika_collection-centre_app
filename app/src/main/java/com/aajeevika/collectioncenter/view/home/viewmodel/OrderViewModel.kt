package com.aajeevika.collectioncenter.view.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aajeevika.collectioncenter.baseclasses.BaseViewModel
import com.aajeevika.collectioncenter.model.data_model.OrderDetailsData
import com.aajeevika.collectioncenter.model.data_model.OrderDetailsListDataModel
import com.aajeevika.collectioncenter.model.data_model.OrderListDataModel
import com.aajeevika.collectioncenter.utility.app_enum.ErrorType
import com.aajeevika.collectioncenter.utility.app_enum.OrderType
import com.aajeevika.collectioncenter.utility.app_enum.ProgressAction

class OrderViewModel : BaseViewModel() {
    private val _orderListLiveData = MutableLiveData<OrderListDataModel>()
    val orderListLiveData: LiveData<OrderListDataModel> = _orderListLiveData

    private val _orderDetailsLiveData = MutableLiveData<OrderDetailsData>()
    val orderDetailsLiveData: LiveData<OrderDetailsData> = _orderDetailsLiveData

    private val _updateOrderLiveData = MutableLiveData<String>()
    val updateOrderLiveData: LiveData<String> = _updateOrderLiveData

    fun getOrderList(userId: Int, collectionCenterId: Int, type: String, page: Int = 1) {
        val requestMap = HashMap<String, Any>()
        requestMap["collection_center_id"] = collectionCenterId
        requestMap["order_type"] = type
        requestMap["user_id"] = userId
        requestMap["page"] = page

        requestData(
            { apiService.getOrderList(requestMap) },
            { it.data?.run { _orderListLiveData.postValue(this) } },
            progressAction = ProgressAction.PROGRESS_BAR,
            errorType = ErrorType.MESSAGE,
        )
    }

    fun getOrderDetails(orderId: Int) {
        val requestMap = HashMap<String, Any>()
        requestMap["orderId"] = orderId

        requestData(
            { apiService.getOrderById(requestMap) },
            { it.data?.all_order?.get(0)?.run { _orderDetailsLiveData.postValue(this) } },
            progressAction = ProgressAction.PROGRESS_DIALOG,
            errorType = ErrorType.TOAST,
        )
    }

    fun updateOrderStatus(orderId: Int, updateTo: OrderType, otp: String = "") {
        val requestMap = HashMap<String, Any>()
        requestMap["order_id"] = orderId
        requestMap["order_status"] = updateTo.type
        if(otp.isNotEmpty()) requestMap["buyer_otp"] = otp

        requestData(
            { apiService.updateOrderStatus(requestMap) },
            { it.message?.run { _updateOrderLiveData.postValue(this) } },
            progressAction = ProgressAction.PROGRESS_DIALOG,
            errorType = ErrorType.DIALOG,
        )
    }
}