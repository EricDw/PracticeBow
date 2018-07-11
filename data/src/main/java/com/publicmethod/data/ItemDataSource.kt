package com.publicmethod.data

class ItemDataSource(private val itemCache: Cache<Item>,
                     private val itemRemote: Remote<Item>) : DataSource<Item> {

    override fun saveItem(a: Item) {
        itemCache.saveItem(a)
    }

    override fun saveItems(a: List<Item>) = itemCache.saveItems(a)

    override fun <String> retrieveItem(id: String): Item? = when (itemCache.isCached(id)) {
        true -> itemCache.retrieveItem(id)
        false -> itemRemote.retrieveItem(id)
    }

    override fun retrieveItems(): List<Item> {
        val cachedItems = itemCache.retrieveItems()
        val cachedItemsIsEmpty = cachedItems.isEmpty()
        return when (cachedItemsIsEmpty) {
            true -> {
                val remoteItems = itemRemote.retrieveItems()
                if (remoteItems.isNotEmpty()) {
                    itemCache.saveItems(remoteItems)
                    remoteItems
                } else {
                    emptyList()
                }
            }
            false -> cachedItems
        }
    }

    override fun <String> isCached(id: String): Boolean = itemCache.isCached(id)
}