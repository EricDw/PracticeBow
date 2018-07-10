package com.publicmethod.practicebow.ui.main

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.publicmethod.practicebow.PBApplication
import com.publicmethod.practicebow.R
import com.publicmethod.practicebow.ViewController
import com.publicmethod.practicebow.getViewModel
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.coroutines.experimental.Unconfined
import kotlinx.coroutines.experimental.channels.consumeEach
import kotlinx.coroutines.experimental.launch

class MainFragment : Fragment(), ViewController<MainState> {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var app: PBApplication

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        instantiateViewModel()
        subscribeToStateChannel()
        getReferenceToApplication()
        issueGetEricCommand()
    }

    private fun subscribeToStateChannel() {
        Log.d("THING", "SUBSCRIBING TO STATE")
        viewModel.stateLiveData.observe(this, Observer {
            Log.d("THING", it.toString())
            it?.run {
                renderState(it)
            }
        })
    }

    private fun issueGetEricCommand() {
        viewModel.issueCommand(
                command = MainCommand.GetEricCommand(
                        dependencies = GetEricDependencies(
                                ericApiService = app.getDependencies())))
    }

    private fun getReferenceToApplication() {
        app = activity!!.application as PBApplication
    }

    private fun instantiateViewModel() {
        viewModel = getViewModel()
    }

    override fun renderState(state: MainState) {
        Log.d("THING", state.toString())
        when (state) {
            is MainState.LoadingState -> state_message.text = "LOADING"
            is MainState.ShowEricState -> state_message.text = state.eric.name
        }
    }
}

