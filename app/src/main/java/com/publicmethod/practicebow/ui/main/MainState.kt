package com.publicmethod.practicebow.ui.main

import com.publicmethod.practicebow.Eric
import com.publicmethod.practicebow.MVCViewModel

sealed class MainState : MVCViewModel.State {
    object LoadingState :   MainState()
    data class ShowEricState(val eric: Eric) : MainState()
}