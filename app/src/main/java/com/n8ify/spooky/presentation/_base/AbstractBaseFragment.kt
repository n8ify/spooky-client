package com.n8ify.spooky.presentation._base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

abstract class AbstractBaseFragment : Fragment() {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPostViewCreatedView()
        initPostViewCreatedListener()
        initPostViewCreatedObserver()
    }

    open fun initPostViewCreatedView(){}
    open fun initPostViewCreatedListener(){}
    open fun initPostViewCreatedObserver(){}
}