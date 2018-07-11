package com.publicmethod.practicebow

import arrow.core.value
import arrow.data.Reader
import arrow.data.map
import com.publicmethod.practicebow.MVC.Action
import com.publicmethod.practicebow.MVC.Command
import com.publicmethod.practicebow.MVC.Commandable
import com.publicmethod.practicebow.MVC.Interpreter
import com.publicmethod.practicebow.MVC.Processor
import com.publicmethod.practicebow.MVC.Reducer
import com.publicmethod.practicebow.MVC.Renderer
import com.publicmethod.practicebow.MVC.Result
import com.publicmethod.practicebow.MVC.State

interface ModelViewCommand<
        C : Command,
        A : Action,
        R : Result,
        S : State>
    : Interpreter<C, A>,
        Processor<A, R>,
        Reducer<R, S>,
        Renderer<S>,
        Commandable<C> {

    override fun issueCommand(command: C) =
            Reader(::interpret)
                    .map(::process)
                    .map(::reduce)
                    .map(::render)
                    .run(command)
                    .value()
}