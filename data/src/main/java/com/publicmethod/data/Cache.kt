package com.publicmethod.data

interface Cache<A, I> : DataSource<A, I> {
    fun isExpired(): Boolean
}