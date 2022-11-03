package com.aajeevika.collectioncenter.model.data_model

data class OrderListDataModel(
    val order_list: ArrayList<ListOrderData>
)

data class ListOrderData(
    val id: Int,
    val order_id_d: String,
    val created_at: String,
    val seller: ListSellerData,
    val items: ArrayList<ListOrderItemData>
)

data class ListSellerData(
    val id: Int,
    val name: String,
    val organization_name: String?,
)

data class ListOrderItemData(
    val id: Int,
    val product: ListOrderProductData,
)

data class ListOrderProductData(
    val id: Int,
    val image_1: String?,
)