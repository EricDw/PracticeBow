package com.publicmethod.practicebow

import arrow.core.*
import arrow.data.EitherT
import arrow.data.EitherTKindedJ
import arrow.data.Reader
import arrow.data.ReaderT
import arrow.effects.ForIO
import arrow.effects.IO
import arrow.effects.IOContext
import arrow.effects.fix
import com.publicmethod.data.Item
import com.publicmethod.data.ItemId
import com.publicmethod.data.Items
import com.publicmethod.practicebow.ItemRemote.ItemException
import com.publicmethod.practicebow.ui.main.GetItemScope
import com.publicmethod.practicebow.ui.main.GetItemsScope

object LeftItemRepo : Repository<ItemException, Item, ItemId> {

    override fun getItem(itemId: ItemId): Either<ItemException, Item> =
            Either.left(ItemException.NoItemFoundException)

    override fun getItems(): Either<ItemException, Items> =
            Either.left(ItemException.NoItemFoundException)

}