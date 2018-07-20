@file:Suppress("PropertyName")

package com.publicmethod.practicebow.threading

import kotlinx.coroutines.experimental.Unconfined
import kotlin.coroutines.experimental.CoroutineContext

class MockContextProvider : ContextProvider() {
    override val UIContext: CoroutineContext = Unconfined
    override val BackgroundContext: CoroutineContext = Unconfined
}