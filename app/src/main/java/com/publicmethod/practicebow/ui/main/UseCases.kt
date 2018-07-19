package com.publicmethod.practicebow.ui.main

import arrow.core.Either
import arrow.core.toT
import arrow.data.State
import com.publicmethod.data.Item
import com.publicmethod.practicebow.ItemRemote.ItemException

typealias LoadItemClickAmount = String
typealias LoadItemsClickAmount = String
typealias ErrorMessage = String

object UseCases {

    fun getItemsUseCase(getItemsScope: GetItemsScope): EitherItems =
            getItemsScope.repository.getItems()

    fun getItemUseCase(getItemScope: GetItemScope): Either<ItemException, Item> =
            getItemScope.repository.getItem(getItemScope.itemId)

    fun updateClickStateUseCase(): State<LoadItemClickAmount, Int> =
            State { clickAmount ->
                val newAmount = clickAmount.toInt().inc()
                newAmount.toString() toT newAmount
            }
}
