package com.publicmethod.practicebow.ui.main

import com.publicmethod.practicebow.MVC.State

sealed class MainState : State {
    data class NoItemsErrorState(val mainModel: MainModel) : MainState()
    data class ShowItemState(val mainModel: MainModel) : MainState()
    data class ShowItemsState(val mainModel: MainModel) : MainState()
    data class InitializationState(val mainModel: MainModel) : MainState()
}