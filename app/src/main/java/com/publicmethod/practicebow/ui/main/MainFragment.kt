package com.publicmethod.practicebow.ui.main

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.publicmethod.practicebow.*
import com.publicmethod.practicebow.ui.main.MainCommand.*
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
        viewModel.stateLiveData.value?.apply { renderState(this) }
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
                command = GetItemCommand(
                        dependencies = GetItemDependencies(
                                itemRepository = app.getItemRepository()),
                        itemId = "itemID",
                        currentLoadItemClickAmount = button_one_clicks.text.toString()))
    }

    private fun issueGetItemsCommand() {
        viewModel.issueCommand(
                command = GetItemsCommand(
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
            is NoItemsErrorState -> renderNoItemErrorState(state)
            is ShowItemState -> renderShowItemState(state)
            is ShowItemsState -> renderShowItemsState(state)
        }
    }

    private fun renderShowItemsState(state: ShowItemsState) =
            with(state) {
                var itemNames = ""
                items.forEach { itemNames += it.name }
                state_message.text = itemNames
            }

    private fun renderShowItemState(state: ShowItemState) {
        with(state) {
            state_message.text = item.name
            button_one_clicks.text = loadItemClickAmount
        }
    }

    private fun renderNoItemErrorState(state: NoItemsErrorState) =
            with(state) {
                state_message.text = errorMessage
                button_one_clicks.text = loadItemClickAmount
            }
}

