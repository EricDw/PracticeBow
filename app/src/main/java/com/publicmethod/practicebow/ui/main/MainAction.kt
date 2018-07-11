package com.publicmethod.practicebow.ui.main

import arrow.core.Option
import arrow.data.Reader
import arrow.effects.IO
import com.publicmethod.data.Item
import com.publicmethod.practicebow.GetItemDependencies
import com.publicmethod.practicebow.MVC.Action

sealed class MainAction : Action {
    data class GetItemAction(
            val reader: Reader<GetItemDependencies, IO<Option<Item>>>,
            val itemDependencies: GetItemDependencies) : MainAction()

    data class GetItemsAction(
            val reader: Reader<GetItemDependencies, IO<Option<List<Item>>>>,
            val itemDependencies: GetItemDependencies) : MainAction()
}

