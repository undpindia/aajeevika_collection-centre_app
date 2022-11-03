package com.aajeevika.collectioncenter.view.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.aajeevika.collectioncenter.baseclasses.BaseRecyclerViewAdapter
import com.aajeevika.collectioncenter.baseclasses.BaseViewHolder
import com.aajeevika.collectioncenter.databinding.ListItemOrderDetailsProductBinding
import com.aajeevika.collectioncenter.model.data_model.OrderDetailsItemsData

class OrderItemsRecyclerViewAdapter(private val dataList: ArrayList<OrderDetailsItemsData>) : BaseRecyclerViewAdapter() {

    override fun createViewHolder(inflater: LayoutInflater, parent: ViewGroup, viewType: Int): BaseViewHolder = run {
        OrderDetailsItemViewHolder(ListItemOrderDetailsProductBinding.inflate(inflater, parent, false))
    }

    override fun getItemCount() = dataList.size

    private inner class OrderDetailsItemViewHolder(val viewDataBinding: ListItemOrderDetailsProductBinding) : BaseViewHolder(viewDataBinding) {
        override fun bindData(context: Context) {
            val data = dataList[adapterPosition]

            viewDataBinding.run {
                name = data.product.name
                quantity = data.quantity.toString()
                priceUnit = data.product.price_unit
                price = (data.quantity * data.product.price.toInt()).toString()
            }
        }
    }
}