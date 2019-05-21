package com.n8ify.spooky.presentation.setup

import android.os.Bundle
import com.n8ify.spooky.R
import com.n8ify.spooky.presentation._base.AbstractBaseActivity
import com.n8ify.spooky.presentation.setup.adapter.SetupPagerAdapter
import com.n8ify.spooky.presentation.setup.fragment.RegisteredSpotFragment
import com.n8ify.spooky.presentation.setup.fragment.SaveSpotFragment
import kotlinx.android.synthetic.main.activity_setup.*
import org.koin.android.viewmodel.ext.android.viewModel

class SetupActivity : AbstractBaseActivity() {

    val spotViewModel: SetupViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setup)
    }

    override fun initPostCreateObserver() {
        vp_setup.adapter = SetupPagerAdapter.getInstance(
            supportFragmentManager,
            RegisteredSpotFragment.getInstance(),
            SaveSpotFragment.getInstance(null)
        )
    }
}
