package com.publicmethod.practicebow.ui.main.algebras

import arrow.data.State
import com.publicmethod.practicebow.Archer.Result
import com.publicmethod.practicebow.ui.main.LoadItemClickAmount
import com.publicmethod.practicebow.ui.main.MainModel

sealed class MainResult : Result {

    data class GetItemResult(
            val itemEither: EitherItem,
            val loadItemClickState: State<LoadItemClickAmount, Int>,
            val getItemsScope: Scopes.GetItemScope)
        : MainResult()

    data class GetItemsResult(val itemsOption: EitherItems)
        : MainResult()

    data class InitializeResult(val mainModel: MainModel)
        : MainResult()
}
