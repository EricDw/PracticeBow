package com.publicmethod.practicebow.ui.main

import com.publicmethod.practicebow.GetItemDependencies
import com.publicmethod.practicebow.MVC.Command

sealed class MainCommand : Command {

    data class GetItemCommand(val dependencies: GetItemDependencies, val itemId: String) : MainCommand()
    data class GetItemsCommand(val dependencies: GetItemDependencies) : MainCommand()

}
