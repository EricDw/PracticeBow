package com.publicmethod.practicebow

import android.app.Application

class PBApplication : Application() {

    fun getDependencies(): ApiService = ApiService()

}