package com.publicmethod.practicebow

import arrow.core.*
import arrow.core.Either.Right.Companion
import com.publicmethod.data.Item
import com.publicmethod.data.ItemId
import com.publicmethod.data.Items
import com.publicmethod.practicebow.ItemRemote.ItemException.NoItemFoundException
import java.util.*

object ItemRemote {

    sealed class ItemException : Exception() {
        object NoItemFoundException : ItemException()
    }

    @Throws(NoItemFoundException::class)
    private fun sometimesItem(): Item =
            when (randomIsEven()) {
                true -> Item()
                else -> throw NoItemFoundException

            }

    @Throws(NoItemFoundException::class)
    private fun sometimesItems(): Items =
            when (randomIsEven()) {
                true -> listOf(Item("1"), Item("2"))
                else -> throw NoItemFoundException
            }

    fun retrieveItem(itemId: ItemId): Either<ItemException, Item> =
            Try { sometimesItem() }.fold(
                    { Left(NoItemFoundException) },
                    { Right(it) }
            )


    fun retrieveItems(): Either<ItemException, Items> =
            Try { sometimesItems() }.fold(
                    { Left(NoItemFoundException) },
                    { Right(it) }
            )

    private fun randomIsEven() = Random().nextInt().isEven()
}
