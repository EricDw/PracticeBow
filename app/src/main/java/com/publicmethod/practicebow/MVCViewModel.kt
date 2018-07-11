@file:Suppress("MemberVisibilityCanBePrivate")

package com.publicmethod.practicebow

import android.arch.lifecycle.ViewModel
import arrow.data.Reader
import arrow.data.map
import com.publicmethod.practicebow.MVC.Action
import com.publicmethod.practicebow.MVC.Command
import com.publicmethod.practicebow.MVC.Interpreter
import com.publicmethod.practicebow.MVC.Processor
import com.publicmethod.practicebow.MVC.Reducer
import com.publicmethod.practicebow.MVC.Renderer
import com.publicmethod.practicebow.MVC.Result
import com.publicmethod.practicebow.MVC.State
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch

abstract class MVCViewModel<
        C : Command,
        A : Action,
        R : Result,
        S : State> : ViewModel() {

    protected abstract val interpreter: Interpreter<C, A>
    protected abstract val processor: Processor<A, R>
    protected abstract val reducer: Reducer<R, S>
    protected abstract val renderer: Renderer<S>

    fun issueCommand(command: C) = launch(CommonPool) {
        Reader(interpreter::interpret)
                .map { processor::process }
                .map { reducer::reduce }
                .map { renderer::render }
                .run(command)
    }
}

