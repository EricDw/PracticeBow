package com.publicmethod.practicebow.ui.main

import arrow.core.fix
import arrow.core.getOrElse
import arrow.core.some
import arrow.core.value
import com.publicmethod.practicebow.MVCViewModel
import com.publicmethod.practicebow.ui.main.MainResult.GetEricResult

object MainReducer : MVCViewModel.Reducer<MainResult, MainState> {
    override fun reduce(result: MainResult): MainState {
        return when (result) {
            is GetEricResult -> {
                with(result) {
                    ericOption.value().fold(
                            { MainState.LoadingState },
                            { MainState.ShowEricState(it) })
                }
            }
        }
    }
}
