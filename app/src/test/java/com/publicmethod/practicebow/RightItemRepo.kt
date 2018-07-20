package com.publicmethod.practicebow

import arrow.core.Either
import com.publicmethod.data.Item
import com.publicmethod.data.ItemId
import com.publicmethod.data.Items
import com.publicmethod.practicebow.algerbras.ItemException

object RightItemRepo : Repository<ItemException, Item, ItemId> {

    override fun getItem(itemId: ItemId): Either<ItemException, Item> =
            Either.right(Item())

    override fun getItems(): Either<ItemException, Items> =
            Either.right(listOf(Item("name 0"), Item("name 1")))

}