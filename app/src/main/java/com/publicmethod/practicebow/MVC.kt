@file:Suppress("FunctionName")

package com.publicmethod.practicebow

import arrow.data.Reader
import arrow.data.run
import arrow.data.runId

object MVC {

//region Interfaces

    interface Command
    interface Action
    interface State
    interface Result
    interface Model

    interface Reducer<R, S> {
        fun reduce(result: R): S
    }

    interface Commandable<C : Command> {
        fun issueCommand(command: C)
    }

//endregion Interfaces

    abstract class CommandInterpreter<C : Command, A : Action> {

        fun interpreterReader(): Reader<C, A> =
                Reader { command -> interpretCommand(command).toId() }

        fun interpret(command: C) = interpreterReader().runId(command)

        protected abstract fun interpretCommand(command: C): A

    }

    abstract class ActionProcessor<A : Action, R : Result> {

        fun processorReader(): Reader<A, R> =
                Reader { action -> processAction(action).toId() }

        fun process(action: A) = processorReader().runId(action)

        protected abstract fun processAction(action: A): R

    }

    abstract class StateReducer<R : Result,
            M : Model,
            S : State
            >(private var _oldModel: M)
        : Reducer<R, S> {

        val oldModel: M
            get() = _oldModel

        override fun reduce(result: R): S {
            val reduction = reduceState(result).run(_oldModel)
            _oldModel = reduction.a
            return reduction.b
        }

        protected abstract fun reduceState(result: R): arrow.data.State<M, S>
    }

    interface ModelViewCommand<C : Command, A : Action, R : Result, S : State, M : Model>
        : Commandable<C> {
        val interpreter: CommandInterpreter<C, A>
        val processor: ActionProcessor<A, R>
        val reducer: StateReducer<R, M, S>
    }
}
