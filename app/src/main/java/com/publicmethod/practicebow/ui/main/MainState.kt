package com.publicmethod.practicebow.ui.main

import com.publicmethod.data.Item
import com.publicmethod.practicebow.MVC.State

sealed class MainState : State {
    data class NoItemsErrorState(val errorMessage: String = "") : MainState()
    data class ShowItemState(val item: Item) : MainState()
    data class ShowItemsState(val items: List<Item>) : MainState()
}