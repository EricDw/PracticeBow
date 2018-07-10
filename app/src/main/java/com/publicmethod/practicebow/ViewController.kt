package com.publicmethod.practicebow

import arrow.data.State

interface ViewController<S> {
    fun renderState(state: S)
}