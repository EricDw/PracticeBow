package com.publicmethod.data

class ItemDataStore(private val itemCache: Cache<Item, ItemId>,
                    private val itemRemote: Remote<Item, ItemId>) : DataSource<Item, ItemId> {

    override fun saveItem(a: Item) {
        itemCache.saveItem(a)
    }

    override fun saveItems(a: Items) = itemCache.saveItems(a)

    override fun retrieveItem(id: ItemId): Item? = when (itemCache.isCached(id)) {
        true -> itemCache.retrieveItem(id)
        false -> itemRemote.retrieveItem(id)
    }

    override fun retrieveItems(): Items {
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

    override fun isCached(id: ItemId): Boolean = itemCache.isCached(id)

    override fun isCached(): Boolean = itemCache.isCached()

}