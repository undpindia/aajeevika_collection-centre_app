package com.aajeevika.collectioncenter.view.grievance.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.aajeevika.collectioncenter.baseclasses.BaseRecyclerViewAdapter
import com.aajeevika.collectioncenter.baseclasses.BaseViewHolder
import com.aajeevika.collectioncenter.databinding.ListItemChatAajeevikaBinding
import com.aajeevika.collectioncenter.databinding.ListItemChatSelfBinding
import com.aajeevika.collectioncenter.model.data_model.MessageData
import com.aajeevika.collectioncenter.utility.UtilityActions

class GrievanceChatRecyclerViewAdapter: BaseRecyclerViewAdapter() {
    private val dataList = ArrayList<MessageData>()

    fun addData(data: ArrayList<MessageData>) {
        dataList.clear()
        dataList.addAll(data)
        notifyDataSetChanged()
    }

    override fun createViewHolder(inflater: LayoutInflater, parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when(viewType) {
            0 -> SelfChatViewHolder(ListItemChatSelfBinding.inflate(inflater, parent, false))
            else -> AajeevikaChatViewHolder(ListItemChatAajeevikaBinding.inflate(inflater, parent, false))
        }
    }

    override fun getItemCount() = dataList.size

    override fun getItemViewType(position: Int): Int {
        return when(dataList[position].type) {
            "by_user" -> 0
            else -> 1
        }
    }

    inner class SelfChatViewHolder(val dataBinding: ListItemChatSelfBinding): BaseViewHolder(dataBinding) {
        override fun bindData(context: Context) {
            val data = dataList[adapterPosition]

            dataBinding.run {
                UtilityActions.parseInterestDate(data.created_at)?.let { time = UtilityActions.formatChatDate(it) }
                message = data.message
            }
        }
    }

    inner class AajeevikaChatViewHolder(val dataBinding: ListItemChatAajeevikaBinding): BaseViewHolder(dataBinding) {
        override fun bindData(context: Context) {
            val data = dataList[adapterPosition]

            dataBinding.run {
                UtilityActions.parseInterestDate(data.created_at)?.let { time = UtilityActions.formatChatDate(it) }
                message = data.message
            }
        }
    }
}