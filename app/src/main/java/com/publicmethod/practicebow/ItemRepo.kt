package com.publicmethod.practicebow

import arrow.core.Either
import com.publicmethod.data.Item
import com.publicmethod.data.ItemId
import com.publicmethod.data.Items
import com.publicmethod.practicebow.algerbras.ItemException

object ItemRepo : Repository<ItemException, Item, ItemId> {

    override fun getItem(itemId: ItemId): Either<ItemException, Item> =
            ItemRemote.retrieveItem(itemId)

    override fun getItems(): Either<ItemException, Items> =
            ItemRemote.retrieveItems()

}