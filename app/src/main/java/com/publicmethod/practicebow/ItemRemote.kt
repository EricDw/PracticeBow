package com.publicmethod.practicebow

import com.publicmethod.data.Item
import com.publicmethod.data.Remote
import java.util.*

class ItemRemote : Remote<Item> {

    private var item: Item? = null
    private var items: List<Item>? = null

    override fun saveItem(a: Item) {
        item = a
    }

    override fun saveItems(a: List<Item>) {
        items = a
    }

    override fun <I> retrieveItem(id: I): Item? = sometimesItem().run { item }

    override fun retrieveItems(): List<Item> = sometimesItems().run { items ?: emptyList() }

    override fun <I> isCached(id: I): Boolean = item?.run { true } ?: false

    private fun sometimesItem() {
        item = when (Random().nextInt().isEven()) {
            true -> Item()
            else -> null
        }
    }

    private fun sometimesItems() {
        items = when (Random().nextInt().isEven()) {
            true -> listOf(Item("1"), Item("2"))
            else -> null
        }
    }
}
