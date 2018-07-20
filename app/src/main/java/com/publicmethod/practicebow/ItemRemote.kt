package com.publicmethod.practicebow

import arrow.core.Either
import arrow.core.Left
import arrow.core.Right
import arrow.core.Try
import com.publicmethod.data.Item
import com.publicmethod.data.ItemId
import com.publicmethod.data.Items
import com.publicmethod.practicebow.algerbras.ItemException
import com.publicmethod.practicebow.extensions.isEven
import java.util.*

object ItemRemote {

    @Throws(ItemException.NoItemFoundException::class)
    private fun sometimesItem(): Item =
            when (randomIsEven()) {
                true -> Item()
                else -> throw ItemException.NoItemFoundException

            }

    @Throws(ItemException.NoItemFoundException::class)
    private fun sometimesItems(): Items =
            when (randomIsEven()) {
                true -> listOf(Item("1"), Item("2"))
                else -> throw ItemException.NoItemFoundException
            }

    fun retrieveItem(itemId: ItemId): Either<ItemException, Item> =
            Try { sometimesItem() }.fold(
                    { Left(ItemException.NoItemFoundException) },
                    { Right(it) }
            )


    fun retrieveItems(): Either<ItemException, Items> =
            Try { sometimesItems() }.fold(
                    { Left(ItemException.NoItemFoundException) },
                    { Right(it) }
            )

    private fun randomIsEven() = Random().nextInt().isEven()
}
