@file:Suppress("DeferredResultUnused")

package com.publicmethod.practicebow.threading

import arrow.Kind
import arrow.core.Try
import arrow.core.right
import arrow.effects.typeclasses.Async

class MockThreader : Threader() {

    override fun <F, A, B> threadAsync(
            f: () -> A,
            onError: (Throwable) -> B,
            onSuccess: (A) -> B, AC: Async<F>): Kind<F, B> {
        return AC.async { proc ->
            val result = Try { f() }.fold(onError, onSuccess)
            proc(result.right())
        }
    }

}