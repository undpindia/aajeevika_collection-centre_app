package com.aajeevika.collectioncenter.view.grievance

import android.os.Bundle
import android.view.View
import com.aajeevika.collectioncenter.R
import com.aajeevika.collectioncenter.baseclasses.BaseActivityVM
import com.aajeevika.collectioncenter.databinding.ActivityGrievanceChatBinding
import com.aajeevika.collectioncenter.utility.Constant
import com.aajeevika.collectioncenter.utility.RecyclerViewDecoration
import com.aajeevika.collectioncenter.view.grievance.adapter.GrievanceChatRecyclerViewAdapter
import com.aajeevika.collectioncenter.view.grievance.viewmodel.GrievanceChatViewModel

class ActivityGrievanceChat: BaseActivityVM<ActivityGrievanceChatBinding, GrievanceChatViewModel>(
    R.layout.activity_grievance_chat,
    GrievanceChatViewModel::class
) {
    private val grievanceChatRecyclerViewAdapter by lazy { GrievanceChatRecyclerViewAdapter() }
    private val ticketIdDisplay by lazy { intent.getStringExtra(Constant.TICKET_ID_DISPLAY) ?: "" }
    private val ticketId by lazy { intent.getIntExtra(Constant.TICKET_ID, -1) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding.title = ticketIdDisplay

        viewDataBinding.recyclerView.run {
            adapter = grievanceChatRecyclerViewAdapter
            addItemDecoration(RecyclerViewDecoration(8F, 4F, 8F, 4F))
        }

        viewModel.getGrievanceTypeList(preferencesHelper.uid, ticketId)
    }

    override fun observeData() {
        super.observeData()

        viewModel.grievanceLiveData.observe(this, {
            stopSwipeToRefreshRefresh()
            grievanceChatRecyclerViewAdapter.addData(it.get_message)
            viewDataBinding.message = "${it.get_issue.name}: ${it.message}"
            viewDataBinding.newMessageContainer.visibility = if(it.status == 0) View.VISIBLE else View.GONE
            viewDataBinding.ticketClosedMessage.visibility = if(it.status == 0) View.GONE else View.VISIBLE
        })

        viewModel.messageLiveData.observe(this, {
            stopSwipeToRefreshRefresh()
            viewDataBinding.inputMessage.text = null
            viewModel.getGrievanceTypeList(preferencesHelper.uid, ticketId)
        })
    }

    override fun initListener() {
        viewDataBinding.run {
            toolbar.backBtn.setOnClickListener {
                onBackPressed()
            }

            sendBtn.setOnClickListener {
                val message = inputMessage.text.toString().trim()
                if(message.isNotEmpty()) viewModel.sendMessage(ticketId, message)
            }

            swipeRefreshLayout.setOnRefreshListener {
                viewModel.getGrievanceTypeList(preferencesHelper.uid, ticketId)
            }
        }
    }
}