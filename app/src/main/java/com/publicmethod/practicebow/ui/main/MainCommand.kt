package com.publicmethod.practicebow.ui.main

import com.publicmethod.practicebow.MVCViewModel

sealed class MainCommand : MVCViewModel.Command {

    data class GetEricCommand(val dependencies: GetEricDependencies) : MainCommand()

}
