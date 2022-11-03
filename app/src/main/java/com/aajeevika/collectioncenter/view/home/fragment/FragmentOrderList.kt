package com.aajeevika.collectioncenter.view.home.fragment

import android.os.Bundle
import android.view.View
import com.aajeevika.collectioncenter.R
import com.aajeevika.collectioncenter.baseclasses.BaseFragmentVM
import com.aajeevika.collectioncenter.databinding.FragmentRecyclerViewBinding
import com.aajeevika.collectioncenter.utility.RecyclerViewDecoration
import com.aajeevika.collectioncenter.utility.app_enum.OrderType
import com.aajeevika.collectioncenter.view.home.adapter.OrderRecyclerViewAdapter
import com.aajeevika.collectioncenter.view.home.viewmodel.OrderViewModel

class FragmentOrderList(private val orderType: OrderType) : BaseFragmentVM<FragmentRecyclerViewBinding, OrderViewModel>(
    R.layout.fragment_recycler_view,
    OrderViewModel::class
) {
    private val orderRecyclerViewAdapter by lazy {OrderRecyclerViewAdapter(orderType){ getData(it) }}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewDataBinding.recyclerView.run {
            adapter = orderRecyclerViewAdapter
            addItemDecoration(RecyclerViewDecoration(8F,8F,8F,8F))
        }
    }

    override fun observeData() {
        super.observeData()

        viewModel.orderListLiveData.observe(this, {
            stopSwipeToRefreshRefresh()
            orderRecyclerViewAdapter.addData(it.order_list, 1, 1)
        })
    }

    override fun onStart() {
        super.onStart()
        orderRecyclerViewAdapter.clearList()
        getData()
    }

    override fun initListener() {
        viewDataBinding.run {
            swipeRefreshLayout.setOnRefreshListener {
                getData()
            }
        }
    }

    private fun getData(page: Int = 1) {
        viewModel.getOrderList(preferencesHelper.uid, preferencesHelper.collectionCenterId, orderType.type, page)
    }
}