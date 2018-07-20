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
import com.publicmethod.practicebow.extensions.getViewModel
import com.publicmethod.practicebow.ui.main.algebras.MainCommand
import com.publicmethod.practicebow.ui.main.algebras.MainCommand.GetItemCommand
import com.publicmethod.practicebow.ui.main.algebras.MainCommand.GetItemsCommand
import com.publicmethod.practicebow.ui.main.algebras.MainState
import com.publicmethod.practicebow.ui.main.algebras.MainState.*
import com.publicmethod.practicebow.ui.main.algebras.Scopes
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var app: PBApplication

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        instantiateViewModel()
        subscribeToStateChannel()
        getReferenceToApplication()
        viewModel.stateLiveData.value?.apply { renderState(this) }
                ?: viewModel.issueCommand(MainCommand.InitializeCommand())
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
                Log.d("MainFragment", "Rendering state")
                renderState(it)
            } ?: Log.d("MainFragment", "State is null")
        })
    }

    private fun issueGetItemCommand() {
        viewModel.issueCommand(
                command = GetItemCommand(
                        getItemScope = Scopes.GetItemScope(
                                itemId = "itemID",
                                repository = app.getItemRepository()
                        )))
    }

    private fun issueGetItemsCommand() {
        viewModel.issueCommand(GetItemsCommand(Scopes.GetItemsScope()))
    }

    private fun getReferenceToApplication() {
        app = activity!!.application as PBApplication
    }

    private fun instantiateViewModel() {
        viewModel = getViewModel()
    }

    private fun renderState(state: MainState) =
            when (state) {
                is NoItemsErrorState -> renderNoItemErrorState(state)
                is ShowItemState -> renderShowItemState(state)
                is ShowItemsState -> renderShowItemsState(state)
                is MainState.InitializationState -> renderInitializationState(state)
            }

    private fun renderInitializationState(state: InitializationState) {
        with(state.mainModel) {
            button_one_clicks.text = currentLoadItemClickAmount
            button_two_clicks.text = currentLoadItemsClickAmount

//            TODO: Handle items and items
        }
    }


    private fun renderShowItemsState(state: ShowItemsState) =
            with(state) {
                var itemNames = ""
                state.mainModel.items.fold({},
                        { it.forEach { itemNames += "$it.name\n" } }
                )
                state_message.text = itemNames
            }

    private fun renderShowItemState(state: ShowItemState) {
        with(state) {
            mainModel.item.fold({}, {
                state_message.text = it.name
            })
            button_one_clicks.text = mainModel.currentLoadItemsClickAmount
        }
    }

    private fun renderNoItemErrorState(state: NoItemsErrorState) =
            with(state) {
                this.mainModel.errorMessage.fold({}, {
                    state_message.text = it
                })
                button_one_clicks.text = mainModel.currentLoadItemClickAmount
            }
}

