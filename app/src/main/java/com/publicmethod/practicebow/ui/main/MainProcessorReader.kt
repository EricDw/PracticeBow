@file:Suppress("FunctionName")

package com.publicmethod.practicebow.ui.main

import com.publicmethod.practicebow.Archer.ActionProcessor
import com.publicmethod.practicebow.ui.main.algebras.MainAction.*
import com.publicmethod.practicebow.ui.main.algebras.MainResult.*
import com.publicmethod.practicebow.ui.main.UseCases.getItemUseCase
import com.publicmethod.practicebow.ui.main.UseCases.updateClickStateUseCase
import com.publicmethod.practicebow.ui.main.algebras.MainAction
import com.publicmethod.practicebow.ui.main.algebras.MainResult

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
