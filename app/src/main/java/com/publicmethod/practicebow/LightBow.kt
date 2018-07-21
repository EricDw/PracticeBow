package com.publicmethod.practicebow

import android.arch.lifecycle.ViewModel
import com.publicmethod.practicebow.Archer.Action
import com.publicmethod.practicebow.Archer.Command
import com.publicmethod.practicebow.Archer.Commandable
import com.publicmethod.practicebow.Archer.Model
import com.publicmethod.practicebow.Archer.ModelViewCommand
import com.publicmethod.practicebow.Archer.Result
import com.publicmethod.practicebow.Archer.State
import com.publicmethod.practicebow.Archer.StateHandler
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.channels.Channel
import kotlinx.coroutines.experimental.channels.actor
import kotlinx.coroutines.experimental.launch
import kotlin.coroutines.experimental.CoroutineContext

abstract class LightBow<C : Command,
        A : Action,
        R : Result,
        S : State,
        M : Model>(
        private val uiContext: CoroutineContext,
        private val backgroundContext: CoroutineContext) : ViewModel(),
        ModelViewCommand<C, A, R, S, M>, Commandable<C>, StateHandler<S> {

    protected val parentJob = Job()

    private val interpreterFletching = actor<C>(
            context = backgroundContext,
            capacity = Channel.UNLIMITED,
            parent = parentJob
    ) {
        for (command in channel) {
            interpreterActor.send(command)
        }
    }

    private val processorFletching = actor<A>(
            context = backgroundContext,
            capacity = Channel.UNLIMITED,
            parent = parentJob
    ) {
        for (action in channel) {
            processorActor.send(action)
        }
    }

    private val reducerFletching = actor<R>(
            context = backgroundContext,
            capacity = Channel.UNLIMITED,
            parent = parentJob
    ) {
        for (result in channel) {
            reducerActor.send(result)
        }
    }

    private val stateFletching = actor<S>(
            context = backgroundContext,
            capacity = Channel.UNLIMITED,
            parent = parentJob
    ) {
        for (state in channel) {
            launch(uiContext) {
                handleState(state)
            }
        }
    }

    private val interpreterActor = actor<C>(
            context = backgroundContext,
            capacity = Channel.UNLIMITED,
            parent = parentJob
    ) {
        for (command in channel) {
            processorFletching.send(interpreter.interpret(command))
        }
    }

    private val processorActor = actor<A>(
            context = backgroundContext,
            capacity = Channel.UNLIMITED,
            parent = parentJob
    ) {
        for (action in channel) {
            reducerFletching.send(processor.process(action))
        }
    }

    private val reducerActor = actor<R>(
            context = backgroundContext,
            capacity = Channel.UNLIMITED,
            parent = parentJob
    ) {
        for (result in channel) {
            stateFletching.send(reducer.reduce(result))
        }
    }

    override fun issueCommand(command: C) {
        launch(backgroundContext) {
            interpreterFletching.send(command)
        }
    }
}

