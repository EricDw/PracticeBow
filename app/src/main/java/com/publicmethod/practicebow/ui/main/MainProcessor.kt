package com.publicmethod.practicebow.ui.main

import arrow.core.value
import arrow.data.run
import com.publicmethod.practicebow.MVC.Processor
import com.publicmethod.practicebow.ui.main.MainAction.GetItemAction
import com.publicmethod.practicebow.ui.main.MainAction.GetItemsAction

object MainProcessor : Processor<MainAction, MainResult> {
    override fun process(action: MainAction): MainResult {
        return when (action) {
            is GetItemAction -> processGetItemAction(action)
            is GetItemsAction -> processGetItemsAction(action)
        }
    }

    private fun processGetItemAction(action: GetItemAction): MainResult.GetItemResult {
        return with(action) {
            MainResult.GetItemResult(
                    reader.run(itemDependencies)
                            .value()
                            .unsafeRunSync(),
                    state.run(currentLoadItemClickAmount).a)
        }
    }

    private fun processGetItemsAction(action: GetItemsAction): MainResult.GetItemsResult {
        return with(action) {
            MainResult.GetItemsResult(reader.run(itemDependencies).value().unsafeRunSync())
        }
    }
}


