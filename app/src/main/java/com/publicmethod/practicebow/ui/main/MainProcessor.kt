package com.publicmethod.practicebow.ui.main

import com.publicmethod.practicebow.MVCViewModel
import com.publicmethod.practicebow.ui.main.MainAction.GetEricAction

object MainProcessor : MVCViewModel.Processor<MainAction, MainResult> {

    override fun process(action: MainAction): MainResult {
        return when (action) {
            is GetEricAction -> {
                val option = with(action) {
                    reader.run(ericDependencies)
                }
                MainResult.GetEricResult(option)
            }
        }
    }
}


