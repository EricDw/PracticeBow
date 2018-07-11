package com.publicmethod.practicebow

import arrow.core.Option
import arrow.core.fix
import com.publicmethod.data.Item
import com.publicmethod.data.ItemDataSource

class ItemRepository(private val dataSource: ItemDataSource) {
    fun getItem(id: String = "") : Option<Item> = Option.fromNullable(dataSource.retrieveItem(id))
    fun getItems(): Option<List<Item>>  = Option(dataSource.retrieveItems()).fix()
}