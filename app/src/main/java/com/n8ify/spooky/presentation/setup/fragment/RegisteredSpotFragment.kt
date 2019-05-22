package com.n8ify.spooky.presentation.setup.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.n8ify.spooky.R
import com.n8ify.spooky.presentation.setup.SetupActivity
import com.n8ify.spooky.presentation.setup.adapter.RegisteredSpotAdapter
import kotlinx.android.synthetic.main.fragment_registered.view.*

class RegisteredSpotFragment : Fragment() {

    val setupViewModel by lazy { (activity as SetupActivity).spotViewModel }

    companion object {
        fun getInstance(): RegisteredSpotFragment {
            return RegisteredSpotFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(context).inflate(R.layout.fragment_registered, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupViewModel.loadSpots()
        setupViewModel.spots.observe(this, Observer {
            view?.let {
                it.rv_spot.apply {
                    setHasFixedSize(true)
                    layoutManager = LinearLayoutManager(context)
                    setupViewModel.spots.value?.let {spots ->
                        adapter = RegisteredSpotAdapter.getInstance(spots)
                    }
                }
            }
        })
    }
}