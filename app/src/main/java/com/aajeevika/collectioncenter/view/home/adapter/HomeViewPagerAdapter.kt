package com.aajeevika.collectioncenter.view.home.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.aajeevika.collectioncenter.utility.app_enum.OrderType
import com.aajeevika.collectioncenter.view.home.fragment.FragmentOrderList

class HomeViewPagerAdapter(fm: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fm, lifecycle) {
    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment {
        return FragmentOrderList(
            when (position) {
                0 -> OrderType.PENDING
                1 -> OrderType.RECEIVED
                else -> OrderType.DELIVERED
            }
        )
    }
}