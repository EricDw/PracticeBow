package com.publicmethod.practicebow.ui.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.publicmethod.practicebow.*
import com.publicmethod.practicebow.MVC.Commandable
import com.publicmethod.practicebow.MVC.Interpreter
import com.publicmethod.practicebow.MVC.Processor
import com.publicmethod.practicebow.MVC.Reducer
import com.publicmethod.practicebow.MVC.Renderer

class MainViewModel : ViewModel(), Commandable<MainCommand> by MainMVCM {

    val stateLiveData: LiveData<MainState>
        get() = mutableStateLiveData

    companion object : Renderer<MainState> {
        private val mutableStateLiveData = MutableLiveData<MainState>()
        override fun render(state: MainState) {
            mutableStateLiveData.postValue(state)
        }
    }
}