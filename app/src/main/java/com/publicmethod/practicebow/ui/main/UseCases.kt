package com.publicmethod.practicebow.ui.main

import arrow.core.Option
import arrow.core.toT
import arrow.data.Reader
import arrow.data.State
import arrow.effects.IO
import arrow.effects.fix
import arrow.effects.monad
import arrow.typeclasses.binding
import com.publicmethod.data.Item
import com.publicmethod.practicebow.GetItemDependencies

typealias loadItemClickAmount = String

object UseCases {
    fun getItemsUseCase(): Reader<GetItemDependencies, IO<Option<List<Item>>>> =
            Reader().lift(::getItemsOption)

    fun getItemUseCase(id: String): Reader<GetItemDependencies, IO<Option<Item>>> =
            Reader().lift { dependencies ->
                IO.monad().binding {
                    dependencies.itemRepository.getItem(id)
                }.fix()
            }

    fun updateClickStateUseCase(): State<loadItemClickAmount, Int> =
            State { it.toInt().inc().toString() toT it.toInt().inc() }


    private fun getItemsOption(dependencies: GetItemDependencies): IO<Option<List<Item>>> {
        return IO.monad().binding {
            dependencies.itemRepository.getItems()
        }.fix()
    }
}
