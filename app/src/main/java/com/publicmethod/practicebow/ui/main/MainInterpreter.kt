package com.publicmethod.practicebow.ui.main

import arrow.core.toOption
import arrow.data.ReaderApi
import com.publicmethod.practicebow.MVCViewModel.Interpreter
import com.publicmethod.practicebow.ui.main.MainAction.GetEricAction
import com.publicmethod.practicebow.ui.main.MainCommand.GetEricCommand

object MainInterpreter : Interpreter<MainCommand, MainAction> {
    override fun interpret(command: MainCommand): MainAction {
        return when (command) {
            is GetEricCommand -> GetEricAction(
                    reader = ReaderApi.lift {
                        return@lift it.ericApiService.getEric().toOption()
                    },
                    ericDependencies = command.dependencies)
        }
    }
}
