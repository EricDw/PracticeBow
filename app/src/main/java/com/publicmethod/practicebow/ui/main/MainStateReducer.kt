package com.publicmethod.practicebow.ui.main

import arrow.core.None
import arrow.core.Option
import arrow.core.Some
import arrow.core.toT
import arrow.data.State
import arrow.data.runS
import com.publicmethod.data.Item
import com.publicmethod.data.Items
import com.publicmethod.practicebow.Archer.StateReducer
import com.publicmethod.practicebow.algerbras.ItemException
import com.publicmethod.practicebow.ui.main.algebras.MainResult
import com.publicmethod.practicebow.ui.main.algebras.MainResult.*
import com.publicmethod.practicebow.ui.main.algebras.MainState
import com.publicmethod.practicebow.ui.main.algebras.MainState.*

object MainStateReducer : StateReducer<MainResult, MainModel, MainState>(MainModel()) {

    override fun reduceState(result: MainResult): State<MainModel, MainState> =
            when (result) {
                is GetItemResult -> getItemReduction(result)
                is GetItemsResult -> getItemsReduction(result)
                is InitializeResult -> getInitializationStateReduction()
            }

    private fun getInitializationStateReduction()
            : State<MainModel, MainState> = State { oldModel ->
        oldModel toT InitializationState(oldModel)
    }

    private fun getItemsReduction(result: GetItemsResult)
            : State<MainModel, MainState> = State { oldModel ->
        lateinit var newState: MainState
        lateinit var newModel: MainModel
        result.itemsOption.fold({
            newModel = noItemsModel(oldModel, it)
            newState = NoItemsErrorState(newModel)
        }, {
            newModel = itemsModel(oldModel, it)
            newState = ShowItemsState(newModel)
        })
        newModel toT newState
    }

    private fun getItemReduction(result: GetItemResult)
            : State<MainModel, MainState> = State { oldModel ->
        lateinit var newState: MainState
        lateinit var newModel: MainModel
        result.itemEither.fold({
            newModel = noItemModel(oldModel, it, result)
            newState = NoItemsErrorState(newModel)
        }, {
            newModel = itemModel(oldModel, it, result)
            newState = ShowItemState(newModel)
        })

        newModel toT newState
    }

    private fun itemModel(oldModel: MainModel, item: Item, result: GetItemResult): MainModel {
        val currentLoadItemClickAmount = result.loadItemClickState.runS(
                oldModel.currentLoadItemClickAmount)

        return oldModel.copy(
                item = Some(item),
                errorMessage = None,
                currentLoadItemClickAmount = currentLoadItemClickAmount
        )
    }

    private fun itemsModel(oldModel: MainModel, it: Items): MainModel {
        return oldModel.copy(
                items = Some(it),
                errorMessage = None)
    }

    private fun noItemsModel(
            oldModel: MainModel,
            exception: ItemException): MainModel =
            oldModel.copy(
                    errorMessage = Option.fromNullable(exception.message))

    private fun noItemModel(
            oldModel: MainModel,
            exception: ItemException,
            result: GetItemResult): MainModel {
        val currentLoadItemClickAmount = result.loadItemClickState.runS(
                oldModel.currentLoadItemsClickAmount)

        return oldModel.copy(
                errorMessage = Option.fromNullable(exception.message),
                currentLoadItemClickAmount = currentLoadItemClickAmount)
    }
}
