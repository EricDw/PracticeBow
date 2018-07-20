package com.publicmethod.practicebow.extensions

import arrow.core.Id

fun <A> A.toId() = Id.just(this)