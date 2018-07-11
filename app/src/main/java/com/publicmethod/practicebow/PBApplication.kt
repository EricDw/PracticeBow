package com.publicmethod.practicebow

import android.app.Application
import com.publicmethod.data.ItemDataSource

class PBApplication : Application() {

    fun getItemRepository(): ItemRepository = ItemRepository(ItemDataSource(
            itemCache = ItemCache(),
            itemRemote = ItemRemote()))

}