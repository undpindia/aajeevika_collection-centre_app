package com.aajeevika.collectioncenter.model.data_model

data class OrderDetailsListDataModel(
    val all_order: ArrayList<OrderDetailsData>
)

data class OrderDetailsData(
    val id: Int,
    val order_id_d: String,
    val order_status: String,
    val created_at: String,
    val interest: OrderDetailsInterestData,
    val buyer: OrderDetailsUserData,
    val seller: OrderDetailsUserData,
    val items: ArrayList<OrderDetailsItemsData>,
)

data class OrderDetailsInterestData(
    val interest_Id: String,
)

data class OrderDetailsUserData(
    val id: Int,
    val name: String,
    val email: String?,
    val mobile: String,
    val organization_name: String?,
)

data class OrderDetailsItemsData(
    val id: Int,
    val quantity: Int,
    val product: OrderDetailsProductData,
)

data class OrderDetailsProductData(
    val id: Int,
    val name: String,
    val price: String,
    val price_unit: String,
)