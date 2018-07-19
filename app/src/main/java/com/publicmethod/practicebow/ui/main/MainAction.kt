package com.publicmethod.practicebow.ui.main

import com.publicmethod.practicebow.MVC.Action

sealed class MainAction : Action {
    data class GetItemAction(val getItemScope: GetItemScope) : MainAction()

    data class GetAllItemsAction(val getItemsScope: GetItemsScope) : MainAction()
    object InitializeAction : MainAction()
}

