package com.publicmethod.practicebow.ui.main

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.publicmethod.practicebow.*
import com.publicmethod.practicebow.ui.main.MainState.*
import kotlinx.android.synthetic.main.main_fragment.*

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
        state_button.setOnClickListener {
            issueGetItemCommand()
        }
        state_button2.setOnClickListener {
            issueGetItemsCommand()
        }
    }

    private fun subscribeToStateChannel() {
        viewModel.stateLiveData.observe(this, Observer {
            it?.run {
                renderState(it)
            }
        })
    }

    private fun issueGetItemCommand() {
        viewModel.issueCommand(
                command = MainCommand.GetItemCommand(
                        dependencies = GetItemDependencies(
                                itemRepository = app.getItemRepository()),
                        itemId = "itemID"))
    }

    private fun issueGetItemsCommand() {
        viewModel.issueCommand(
                command = MainCommand.GetItemsCommand(
                        dependencies = GetItemDependencies(
                                itemRepository = app.getItemRepository())))
    }

    private fun getReferenceToApplication() {
        app = activity!!.application as PBApplication
    }

    private fun instantiateViewModel() {
        viewModel = getViewModel()
    }

    override fun renderState(state: MainState) {
        when (state) {
            is NoItemsErrorState -> state_message.text = state.errorMessage
            is ShowItemState -> state_message.text = state.item.name
            is ShowItemsState -> {
                var items = ""
                state.items.forEach { items += it.name }
                state_message.text = items
            }
        }
    }
}

