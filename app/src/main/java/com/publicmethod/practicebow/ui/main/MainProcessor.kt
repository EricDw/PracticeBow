package com.publicmethod.practicebow.ui.main

import arrow.core.value
import com.publicmethod.practicebow.MVCViewModel.Processor
import com.publicmethod.practicebow.ui.main.MainAction.GetEricAction

object MainProcessor : Processor<MainAction, MainResult> {
    override fun process(action: MainAction): MainResult {
        return when (action) {
            is GetEricAction -> {
                val option = with(action) {
                    reader.run(ericDependencies)
                }.value()
                MainResult.GetEricResult(option)
            }
        }
    }
}


