package com.publicmethod.practicebow.ui.main.algebras

import com.publicmethod.data.Item
import com.publicmethod.data.ItemId
import com.publicmethod.practicebow.ItemRemote
import com.publicmethod.practicebow.ItemRepo
import com.publicmethod.practicebow.Repository
import com.publicmethod.practicebow.algerbras.ItemException

object Scopes {

    data class GetItemScope(
            val itemId: ItemId,
            val repository: Repository<ItemException, Item, ItemId> = ItemRepo)

    data class GetItemsScope(
            val repository: Repository<ItemException, Item, ItemId> = ItemRepo)

}