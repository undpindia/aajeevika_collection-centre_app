package com.aajeevika.collectioncenter.view.home

import android.os.Bundle
import android.widget.Toast
import com.aajeevika.collectioncenter.R
import com.aajeevika.collectioncenter.baseclasses.BaseActivityVM
import com.aajeevika.collectioncenter.databinding.ActivityOrderDetailsBinding
import com.aajeevika.collectioncenter.model.data_model.OrderDetailsData
import com.aajeevika.collectioncenter.utility.Constant
import com.aajeevika.collectioncenter.utility.UtilityActions
import com.aajeevika.collectioncenter.utility.UtilityActions.showMessage
import com.aajeevika.collectioncenter.utility.app_enum.OrderType
import com.aajeevika.collectioncenter.view.home.adapter.OrderItemsRecyclerViewAdapter
import com.aajeevika.collectioncenter.view.home.viewmodel.OrderViewModel

class ActivityOrderDetails : BaseActivityVM<ActivityOrderDetailsBinding, OrderViewModel>(
    R.layout.activity_order_details,
    OrderViewModel::class
) {
    private lateinit var orderDetailsData: OrderDetailsData
    private val orderId by lazy { intent.getIntExtra(Constant.ORDER_ID, -1) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getOrderDetails(orderId)
    }

    override fun observeData() {
        super.observeData()

        viewModel.orderDetailsLiveData.observe(this, {
            orderDetailsData = it

            viewDataBinding.orderDisplayId = it.order_id_d
            viewDataBinding.interestDisplayId = it.interest.interest_Id
            viewDataBinding.buyerName = it.buyer.name
            viewDataBinding.buyerMobile = it.buyer.mobile
            viewDataBinding.buyerEmail = it.buyer.email
            viewDataBinding.sellerName = it.seller.organization_name ?: it.seller.name
            viewDataBinding.sellerMobile = it.seller.mobile
            viewDataBinding.sellerEmail = it.seller.email

            viewDataBinding.orderType = it.order_status

            UtilityActions.parseInterestDate(it.created_at)?.let { date ->
                viewDataBinding.orderDate = UtilityActions.formatInterestDate(date)
            }

            var totalAmount = 0
            it.items.forEach { item -> totalAmount += item.quantity * item.product.price.toInt() }
            viewDataBinding.totalAmount = totalAmount.toString()

            viewDataBinding.recyclerView.adapter = OrderItemsRecyclerViewAdapter(it.items)
        })

        viewModel.updateOrderLiveData.observe(this, {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            finish()
        })
    }

    override fun initListener() {
        viewDataBinding.run {
            toolbar.backBtn.setOnClickListener {
                onBackPressed()
            }

            markReceivedBtn.setOnClickListener {
                if(::orderDetailsData.isInitialized) {
                    viewModel.updateOrderStatus(orderDetailsData.id, OrderType.RECEIVED)
                }
            }

            markDeliveredBtn.setOnClickListener {
                if(::orderDetailsData.isInitialized) {
                    val otp = inputOtp.text.toString().trim()

                    if(otp.length != 4) {
                        root.showMessage(getString(R.string.enter_valid_otp))
                    } else {
                        viewModel.updateOrderStatus(orderDetailsData.id, OrderType.DELIVERED, otp)
                    }
                }
            }
        }
    }
}