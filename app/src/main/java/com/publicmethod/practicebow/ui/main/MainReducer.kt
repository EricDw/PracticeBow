package com.publicmethod.practicebow.ui.main

import com.publicmethod.practicebow.MVCViewModel
import com.publicmethod.practicebow.ui.main.MainResult.GetEricResult

object MainReducer : MVCViewModel.Reducer<MainResult, MainState> {
    override fun reduce(result: MainResult): MainState {
        return when (result) {
            is GetEricResult -> {
                with(result) {
                    ericOption.fold(
                            { MainState.LoadingState },
                            { MainState.ShowEricState(it) })
                }
            }
        }
    }
}
