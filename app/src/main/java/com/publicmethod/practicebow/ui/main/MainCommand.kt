package com.publicmethod.practicebow.ui.main

import com.publicmethod.practicebow.MVC.Command
import com.publicmethod.practicebow.Threader

sealed class MainCommand(open val threader: Threader = Threader()) : Command {

    data class GetItemCommand(
            val getItemScope: GetItemScope,
            override val threader: Threader = Threader()
    ) : MainCommand(threader)

    data class GetItemsCommand(
            val getItemScope: GetItemsScope,
            override val threader: Threader = Threader()
    ) : MainCommand(threader)

    data class InitializeCommand(override val threader: Threader = Threader())
        : MainCommand(threader)
}
