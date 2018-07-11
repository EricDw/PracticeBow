package com.publicmethod.practicebow.ui.main

import com.publicmethod.practicebow.MVC.Interpreter
import com.publicmethod.practicebow.ui.main.MainAction.GetItemAction
import com.publicmethod.practicebow.ui.main.MainAction.GetItemsAction
import com.publicmethod.practicebow.ui.main.MainCommand.GetItemCommand
import com.publicmethod.practicebow.ui.main.MainCommand.GetItemsCommand

object MainInterpreter : Interpreter<MainCommand, MainAction> {
    override fun interpret(command: MainCommand): MainAction =
            when (command) {
                is GetItemCommand -> processGetItemCommand(command)
                is GetItemsCommand -> processGetItemsCommand(command)

            }

    private fun processGetItemCommand(command: GetItemCommand): GetItemAction =
            with(command) {
                GetItemAction(
                        reader = UseCases.getItemUseCase(itemId),
                        state = UseCases.updateClickStateUseCase(),
                        currentLoadItemClickAmount = currentLoadItemClickAmount,
                        itemDependencies = dependencies)
            }


    private fun processGetItemsCommand(command: GetItemsCommand): GetItemsAction =
            with(command) {
                GetItemsAction(
                        reader = UseCases.getItemsUseCase(),
                        itemDependencies = dependencies)
            }
}
