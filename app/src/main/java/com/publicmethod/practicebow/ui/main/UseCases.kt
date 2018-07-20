package com.publicmethod.practicebow.ui.main

import arrow.core.Either
import arrow.core.toT
import arrow.data.State
import com.publicmethod.data.Item
import com.publicmethod.practicebow.algerbras.ItemException
import com.publicmethod.practicebow.ui.main.algebras.EitherItems
import com.publicmethod.practicebow.ui.main.algebras.Scopes

typealias LoadItemClickAmount = String
typealias LoadItemsClickAmount = String
typealias ErrorMessage = String

object UseCases {

    fun getItemsUseCase(getItemsScope: Scopes.GetItemsScope): EitherItems =
            getItemsScope.repository.getItems()

    fun getItemUseCase(getItemScope: Scopes.GetItemScope): Either<ItemException, Item> =
            getItemScope.repository.getItem(getItemScope.itemId)

    fun updateClickStateUseCase(): State<LoadItemClickAmount, Int> =
            State { clickAmount ->
                val newAmount = clickAmount.toInt().inc()
                newAmount.toString() toT newAmount
            }
}
