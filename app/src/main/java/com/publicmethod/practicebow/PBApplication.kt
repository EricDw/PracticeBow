package com.publicmethod.practicebow

import android.app.Application
import com.publicmethod.data.ItemDataStore

class PBApplication : Application() {
    fun getItemRepository(): ItemRepo = ItemRepo
}