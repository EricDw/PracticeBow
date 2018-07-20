@file:Suppress("PropertyName")

package com.publicmethod.practicebow.threading

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlin.coroutines.experimental.CoroutineContext

open class ContextProvider {
    open val UIContext: CoroutineContext = UI
    open val BackgroundContext: CoroutineContext = CommonPool
}