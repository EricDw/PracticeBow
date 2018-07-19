@file:Suppress("FunctionName")

package com.publicmethod.practicebow.ui.main

import com.publicmethod.practicebow.MVC.ActionProcessor
import com.publicmethod.practicebow.ui.main.MainAction.*
import com.publicmethod.practicebow.ui.main.MainResult.*
import com.publicmethod.practicebow.ui.main.UseCases.getItemUseCase
import com.publicmethod.practicebow.ui.main.UseCases.updateClickStateUseCase

object MainProcessorReader : ActionProcessor<MainAction, MainResult>() {
    override fun processAction(action: MainAction): MainResult =
            when (action) {
                is GetItemAction -> processGetItemAction(action)
                is GetAllItemsAction -> processGetItemsAction(action)
                is InitializeAction -> processInitializeAction()
            }

    private fun processInitializeAction(): InitializeResult =
            MainResult.InitializeResult(MainModel())

    private fun processGetItemAction(action: GetItemAction): GetItemResult =
            with(action) {
                val eitherItem = getItemUseCase(getItemScope)
                val clickAmountState = updateClickStateUseCase()
                GetItemResult(eitherItem, clickAmountState, getItemScope)
            }

    private fun processGetItemsAction(action: GetAllItemsAction): GetItemsResult =
            with(action) {
                val items = UseCases.getItemsUseCase(getItemsScope)
                GetItemsResult(items)
            }
}
