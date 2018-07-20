package com.publicmethod.practicebow

import android.app.Application

class PBApplication : Application() {
    fun getItemRepository(): ItemRepo = ItemRepo
}