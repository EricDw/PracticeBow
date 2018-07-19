@file:Suppress("DeferredResultUnused")

package com.publicmethod.practicebow

import arrow.Kind
import arrow.core.Try
import arrow.core.left
import arrow.core.right
import arrow.effects.typeclasses.Async
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async

open class Threader {

    open fun <F, A, B> threadAsync(
            f: () -> A,
            onError: (Throwable) -> B,
            onSuccess: (A) -> B, AC: Async<F>): Kind<F, B> {
        return AC.async { proc ->
            async(CommonPool) {
                val result = Try { f() }.fold(onError, onSuccess)
                proc(result.right())
            }
        }
    }

}