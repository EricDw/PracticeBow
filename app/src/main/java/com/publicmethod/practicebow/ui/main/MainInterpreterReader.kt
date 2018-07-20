package com.publicmethod.practicebow.ui.main

import com.publicmethod.practicebow.Λrcher.CommandInterpreter
import com.publicmethod.practicebow.ui.main.algebras.MainAction
import com.publicmethod.practicebow.ui.main.algebras.MainAction.*
import com.publicmethod.practicebow.ui.main.algebras.MainCommand
import com.publicmethod.practicebow.ui.main.algebras.MainCommand.*

object MainInterpreterReader : CommandInterpreter<MainCommand, MainAction>() {

    override fun interpretCommand(command: MainCommand) =
            when (command) {
                is GetItemCommand -> interpretGetItemCommand(command)
                is GetItemsCommand -> interpretGetItemsCommand(command)
                is InitializeCommand -> interpretInitializeCommand()
            }

    private fun interpretInitializeCommand(): MainAction =
            InitializeAction

    private fun interpretGetItemCommand(command: GetItemCommand): GetItemAction =
            with(command) { GetItemAction(getItemScope) }

    private fun interpretGetItemsCommand(command: GetItemsCommand): GetAllItemsAction =
            with(command) {
                GetAllItemsAction(getItemScope)
            }
}