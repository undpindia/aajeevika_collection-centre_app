package com.aajeevika.collectioncenter.model.network_request

import com.aajeevika.collectioncenter.model.data_model.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @GET("api/getprofile")
    suspend fun getProfile(): Response<BaseDataModel<UserProfileModel>>

    @FormUrlEncoded
    @POST("api/login")
    suspend fun loginUser(@FieldMap body: HashMap<String, Any>): Response<BaseDataModel<UserProfileModel>>

    @FormUrlEncoded
    @POST("api/verifyotp")
    suspend fun verifyOtp(@FieldMap body: HashMap<String, Any>): Response<BaseDataModel<UserProfileModel>>

    @FormUrlEncoded
    @POST("api/resendotp")
    suspend fun resendOtp(@FieldMap body: HashMap<String, Any>): Response<BaseDataModel<OtpModel>>

    @FormUrlEncoded
    @POST("api/forgetpassword")
    suspend fun forgotPassword(@FieldMap body: HashMap<String, Any>): Response<BaseDataModel<OtpModel>>

    @FormUrlEncoded
    @POST("api/changepassword")
    suspend fun changePassword(@FieldMap body: HashMap<String, Any>): Response<BaseDataModel<Any>>

    @FormUrlEncoded
    @POST("api/get-order")
    suspend fun getOrderList(@FieldMap body: HashMap<String, Any>): Response<BaseDataModel<OrderListDataModel>>

    @FormUrlEncoded
    @POST("api/get_order_byid")
    suspend fun getOrderById(@FieldMap body: HashMap<String, Any>): Response<BaseDataModel<OrderDetailsListDataModel>>

    @FormUrlEncoded
    @POST("api/update-order-status")
    suspend fun updateOrderStatus(@FieldMap body: HashMap<String, Any>): Response<BaseDataModel<Any>>

    @FormUrlEncoded
    @POST("api/get-grievance-type-list")
    suspend fun getGrievanceTypeList(@FieldMap body: HashMap<String, Any> = HashMap()): Response<BaseDataModel<GrievanceTypeDataModel>>

    @FormUrlEncoded
    @POST("api/add-grievance-ticket")
    suspend fun addGrievanceTicket(@FieldMap body: HashMap<String, Any>): Response<BaseDataModel<Any>>

    @FormUrlEncoded
    @POST("api/get-ticket-list")
    suspend fun getTicketList(@FieldMap body: HashMap<String, Any>): Response<BaseDataModel<TicketsDataModel>>

    @FormUrlEncoded
    @POST("api/get-ticket-chat-list")
    suspend fun getTicketChatList(@FieldMap body: HashMap<String, Any>): Response<BaseDataModel<GrievanceChatDataModel>>

    @FormUrlEncoded
    @POST("api/add-grievance-message")
    suspend fun sendMessage(@FieldMap body: HashMap<String, Any>): Response<BaseDataModel<Any>>
}