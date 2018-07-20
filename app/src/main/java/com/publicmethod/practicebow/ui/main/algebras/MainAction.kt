package com.publicmethod.practicebow.ui.main.algebras

import com.publicmethod.practicebow.Λrcher.Action

sealed class MainAction : Action {
    data class GetItemAction(val getItemScope: Scopes.GetItemScope) : MainAction()
    data class GetAllItemsAction(val getItemsScope: Scopes.GetItemsScope) : MainAction()
    object InitializeAction : MainAction()
}

