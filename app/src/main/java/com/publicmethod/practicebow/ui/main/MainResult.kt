package com.publicmethod.practicebow.ui.main

import arrow.core.Either
import arrow.core.Option
import arrow.core.Tuple2
import arrow.data.State
import com.publicmethod.data.Item
import com.publicmethod.data.Items
import com.publicmethod.practicebow.ItemRemote
import com.publicmethod.practicebow.ItemRemote.ItemException
import com.publicmethod.practicebow.MVC.Result

typealias EitherItems = Either<ItemException, Items>

typealias EitherItem = Either<ItemException, Item>

sealed class MainResult : Result {

    data class GetItemResult(
            val itemEither: EitherItem,
            val loadItemClickState: State<LoadItemClickAmount, Int>,
            val getItemsScope: GetItemScope)
        : MainResult()

    data class GetItemsResult(val itemsOption: EitherItems)
        : MainResult()

    data class InitializeResult(val mainModel: MainModel)
        : MainResult()
}
