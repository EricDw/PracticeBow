package com.publicmethod.practicebow.ui.main

import arrow.core.Option
import arrow.data.Reader
import arrow.effects.IO
import arrow.effects.fix
import arrow.effects.monad
import arrow.typeclasses.binding
import com.publicmethod.data.Item
import com.publicmethod.practicebow.GetItemDependencies

object UseCases {
    fun getItemsUseCase(): Reader<GetItemDependencies, IO<Option<List<Item>>>> =
            Reader().lift(::getItemsOption)

    fun getItemUseCase(id: String): Reader<GetItemDependencies, IO<Option<Item>>> =
            Reader().lift { dependencies ->
                IO.monad().binding {
                    dependencies.itemRepository.getItem(id)
                }.fix()
            }

    private fun getItemsOption(dependencies: GetItemDependencies): IO<Option<List<Item>>> {
        return IO.monad().binding {
            dependencies.itemRepository.getItems()
        }.fix()
    }
}
