package com.publicmethod.practicebow.ui.main

import com.publicmethod.practicebow.MVC.Reducer
import com.publicmethod.practicebow.ui.main.MainResult.GetItemResult
import com.publicmethod.practicebow.ui.main.MainResult.GetItemsResult
import com.publicmethod.practicebow.ui.main.MainState.*

object MainReducer : Reducer<MainResult, MainState> {
    override fun reduce(result: MainResult): MainState =
            when (result) {
                is GetItemResult -> reduceGetItemResult(result)
                is GetItemsResult -> reduceGetItemsResult(result)
            }

    private fun reduceGetItemResult(result: GetItemResult): MainState =
            result.itemOption.fold(
                    ifEmpty = { NoItemsErrorState("No Item Found") },
                    ifSome = { ShowItemState(it) })

    private fun reduceGetItemsResult(result: GetItemsResult): MainState =
            result.itemOption.fold(
                    ifEmpty = { NoItemsErrorState("No Items Found") },
                    ifSome = {
                        return when {
                            it.isEmpty() -> NoItemsErrorState("No Items Found")
                            else -> ShowItemsState(it)
                        }
                    })
}

