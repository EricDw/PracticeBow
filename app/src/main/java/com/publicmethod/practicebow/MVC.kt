package com.publicmethod.practicebow

object MVC {

//region Interfaces

    interface Command
    interface Action
    interface State
    interface Result

    interface Interpreter<C, A> {
        fun interpret(command: C): A
    }

    interface Processor<A, R> {
        fun process(action: A): R
    }

    interface Reducer<R, S> {
        fun reduce(result: R): S
    }

    interface Renderer<S> {
        fun render(state: S)
    }

    interface Commandable<C> {
        fun issueCommand(command: C)
    }
//endregion Interfaces

}
