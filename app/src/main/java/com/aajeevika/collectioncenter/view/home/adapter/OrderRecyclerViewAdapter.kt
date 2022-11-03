package com.aajeevika.collectioncenter.view.home.adapter

import BaseUrls
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aajeevika.collectioncenter.baseclasses.BaseRecyclerViewAdapter
import com.aajeevika.collectioncenter.baseclasses.BaseViewHolder
import com.aajeevika.collectioncenter.databinding.ListItemLoadMoreBinding
import com.aajeevika.collectioncenter.databinding.ListItemMyOrderBinding
import com.aajeevika.collectioncenter.model.data_model.ListOrderData
import com.aajeevika.collectioncenter.utility.Constant
import com.aajeevika.collectioncenter.utility.UtilityActions
import com.aajeevika.collectioncenter.utility.app_enum.OrderType
import com.aajeevika.collectioncenter.view.home.ActivityOrderDetails

class OrderRecyclerViewAdapter (
    private val orderType: OrderType,
    private val requestData: (Int) -> Unit
) : BaseRecyclerViewAdapter() {
    private val dataList = ArrayList<ListOrderData>()
    private var isNextPageRequested = false
    private var currentPage: Int = -1
    private var lastPage: Int = -1

    fun clearList() {
        dataList.clear()
        isNextPageRequested = false
        currentPage = -1
        lastPage = -1

        notifyDataSetChanged()
    }

    fun addData(data: ArrayList<ListOrderData>, currentPage: Int, lastPage: Int) {
        isNextPageRequested = false

        this.currentPage = currentPage
        this.lastPage = lastPage

        if(currentPage == 1) dataList.clear()
        val currentDataLisSize = dataList.size
        dataList.addAll(data)

        if(currentPage == 1) notifyDataSetChanged()
        else notifyItemRangeChanged(currentDataLisSize, dataList.size - currentDataLisSize)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val lastVisibleItemPosition = (recyclerView.layoutManager as? LinearLayoutManager)?.findLastVisibleItemPosition()

                if(lastVisibleItemPosition == dataList.size && !isNextPageRequested) {
                    isNextPageRequested = true
                    requestData(currentPage + 1)
                }
            }
        })
    }

    override fun createViewHolder(inflater: LayoutInflater, parent: ViewGroup, viewType: Int): BaseViewHolder? = run {
        when(viewType) {
            1 -> OrderListViewHolder(ListItemMyOrderBinding.inflate(inflater, parent, false))
            0 -> LoadMoreViewHolder(ListItemLoadMoreBinding.inflate(inflater, parent, false))
            else -> null
        }
    }

    override fun getItemCount() = dataList.size + if(currentPage < lastPage) 1 else 0

    override fun getItemViewType(position: Int) = if(position < dataList.size) 1 else 0

    private inner class OrderListViewHolder(val viewDataBinding: ListItemMyOrderBinding) : BaseViewHolder(viewDataBinding) {
        override fun bindData(context: Context) {
            val data = dataList[adapterPosition]

            viewDataBinding.run {
                sellerName = data.seller.organization_name ?: data.seller.name
                orderId = data.order_id_d

                UtilityActions.parseInterestDate(data.created_at)?.let { date ->
                    orderDate = UtilityActions.formatInterestDate(date)
                }

                if(data.items.isNotEmpty()) {
                    productImage = data.items[0].product.image_1?.let { image -> BaseUrls.baseUrl + image }
                }

                root.setOnClickListener {
                    val intent = Intent(context, ActivityOrderDetails::class.java)
                    intent.putExtra(Constant.ORDER_ID, data.id)
                    context.startActivity(intent)
                }
            }
        }
    }

    private inner class LoadMoreViewHolder(viewDataBinding: ListItemLoadMoreBinding) : BaseViewHolder(viewDataBinding) {
        override fun bindData(context: Context) {}
    }
}