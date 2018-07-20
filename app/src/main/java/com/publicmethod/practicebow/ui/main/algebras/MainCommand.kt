package com.publicmethod.practicebow.ui.main.algebras

import com.publicmethod.practicebow.Λrcher.Command
import com.publicmethod.practicebow.threading.Threader

sealed class MainCommand(open val threader: Threader = Threader()) : Command {

    data class GetItemCommand(
            val getItemScope: Scopes.GetItemScope,
            override val threader: Threader = Threader()
    ) : MainCommand(threader)

    data class GetItemsCommand(
            val getItemScope: Scopes.GetItemsScope,
            override val threader: Threader = Threader()
    ) : MainCommand(threader)

    data class InitializeCommand(override val threader: Threader = Threader())
        : MainCommand(threader)
}
