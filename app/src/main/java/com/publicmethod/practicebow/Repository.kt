package com.publicmethod.practicebow

import arrow.core.Either

interface Repository<E, D, I> {
    fun getItem(itemId: I): Either<E, D>
    fun getItems(): Either<E, List<D>>
}
