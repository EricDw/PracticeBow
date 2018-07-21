package com.publicmethod.practicebow.ui.main.algebras

import com.publicmethod.practicebow.Archer.State
import com.publicmethod.practicebow.ui.main.MainModel

sealed class MainState : State {
    data class NoItemsErrorState(val mainModel: MainModel) : MainState()
    data class ShowItemState(val mainModel: MainModel) : MainState()
    data class ShowItemsState(val mainModel: MainModel) : MainState()
    data class InitializationState(val mainModel: MainModel) : MainState()
}