package com.n8ify.spooky.presentation.setup.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class SetupPagerAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {

    private lateinit var fragments: List<out Fragment>

    companion object {
        fun getInstance(fm: FragmentManager?, vararg fragments: Fragment): SetupPagerAdapter {
            return SetupPagerAdapter(fm).apply {
                this@apply.fragments = fragments.toMutableList()
            }
        }
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }
}