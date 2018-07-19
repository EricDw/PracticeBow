package com.publicmethod.practicebow

import arrow.core.Id

fun <A> A.toId() = Id.just(this)